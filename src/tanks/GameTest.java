package tanks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameTest extends Scene {

	
	private static Group root = new Group();
	boolean moving=false;
	double mouseX, mouseY;
	List<Wall> wallList = new ArrayList<Wall>();
	List<Bullet> bulletList = new ArrayList<Bullet>();
    Wall wall;
    Bullet bullet;
    boolean hostWon;
    boolean clientWon;
    String clientWonString = "THE CLIENT WINS!";
    String hostWonString = "THE HOST WINS!";
    Font endMessageFont = Font.font("Arial", FontWeight.BOLD, 50);
    Font fpsFont = Font.font("Arial",FontWeight.NORMAL, 15);
    
	public GameTest() {
		super(root);
		
		double framerate;
		
		
		//Setting up the canvas
		Canvas canvas = new Canvas(1200,800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Setting the background
		ImagePattern background = new ImagePattern(new Image("StoneFloorTexture2.jpg"),0.1,0.1,0.1,0.1,true);
		gc.setFill(background);
		gc.fillRect(0,0,1200,800);
		gc.setFill( Color.RED );
	    root.getChildren().add(canvas);	    
	    
	    //Timer setup
	    final long startNanoTime = System.nanoTime();
	    
	    //Loading images
	    Image tankIMG = new Image("tankBase.png");
	    Image wallIMG = new Image("wallTest.jpg");
	    Image tankTurretIMG= new Image("tankTurret.png");
	    
	    //Creating player objects
	    Tank tankHost = new Tank(tankIMG, tankTurretIMG, 500,500, true);
	    Tank tankClient = new Tank(tankIMG, tankTurretIMG, 1000, 50, true);
	    
	    //Creating walls
	    for(int i=0;i<20;i++) {
	    	wall = new Wall(wallIMG, (double)(i*32),(double)(700));
	    	wallList.add(wall);
	    }
	    
	    //Input
	    this.setOnMouseMoved(e->{
	    	mouseX=e.getSceneX();
	    	mouseY=e.getSceneY();
	    	tankHost.updateTurretRotation(mouseX, mouseY);
	    	
	    });
	    
	    this.setOnKeyPressed(e->{
	    	if(e.getCode()==KeyCode.LEFT) {
	    		tankHost.turnLeft();
	    		
	    	}
	    	
	    	if(e.getCode()==KeyCode.RIGHT) {
	    		tankHost.turnRight();
	    	}
	    	if(e.getCode()==KeyCode.UP) {
	    		
	    		tankHost.goForward();
	    		
	    	}
	    	if(e.getCode()==KeyCode.DOWN) {
	    		tankHost.goBack();
	    	}
	    });
	    
	    this.setOnKeyReleased(e->{

	    	if(e.getCode()==KeyCode.UP) {
	    		tankHost.stop();
	    	}
	    	if(e.getCode()==KeyCode.DOWN) {
	    		tankHost.stop();
	    	}
	    });
	    
	    this.setOnMousePressed(e->{
	    	mouseX=e.getSceneX();
	    	mouseY=e.getSceneY();
	    	tankHost.updateTurretRotation(mouseX, mouseY);
	    	tankHost.shoot(mouseX, mouseY, bulletList);
	    });
	    
	    
	    //Ticking
	    new AnimationTimer()
	    {
	    	
	        private final long[] frameTimes = new long[100];
	        private boolean arrayFilled = false;
	        private int frameTimeIndex = 0;
	        public void handle(long currentNanoTime)
	        {

	            System.out.print(java.lang.Thread.activeCount());
	 
	            // background image clears canvas
	    		gc.setFill(background);
	    		gc.fillRect(0,0,1200,800);
	    		

	            
	            //Updating bullets
	    		BulletUpdater BU = new BulletUpdater(bulletList, wallList, tankHost, tankClient);
	    		BU.start();
	    		
	    		/*
	    		
	            for(int i=0;i<bulletList.size();i++) {
	            	bullet=bulletList.get(i);
	            	
	            	if(!bullet.update(1, wallList, bulletList, i)) {
		            	if(bullet.getPosX()<0||bullet.getPosX()>1300||bullet.getPosY()<0||bullet.getPosY()>1300) {
		            		//Removing bullets if they go out of bounds
		            		bulletList.remove(i);
		            	}
	            	}

	            	
	            }
	            */

	            //Updating the players
	    		PlayerUpdater PU = new PlayerUpdater(mouseX,mouseY,wallList,tankHost,tankClient);
	    		PU.start();
	    		
	    		 System.out.print("->"+java.lang.Thread.activeCount());
	    		/*
	            tankHost.updateTurretRotation(mouseX, mouseY);
	            tankHost.update(1, wallList);
	            tankHost.tickCooldown();
	    		tankClient.update(1,wallList);
	    		tankClient.tickCooldown();
	    		*/
	    		
	    		try {
					BU.join();
					PU.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
	    		
	    		
	            //Rendering the players
	    		if(!clientWon) {
	    			tankHost.render(gc);
	    		}
	    		
	    		if(!hostWon) {
	    			tankClient.render(gc);
	    		}
	            
	    		
	    		
	    		//Rendering walls
	            for(int i=0;i<wallList.size();i++) {
	            	if(wallList.get(i).getHp()<=0) {
	            		wallList.remove(i);
	            	}else {
	            		wallList.get(i).render(gc);
	            	}
	            	
	            }
	    		
	            //Rendering bullets
	            for(int i=0;i<bulletList.size();i++) {
	            	bulletList.get(i).render(gc);
	            }
	            
	            
	    		//Checking win conditions
	    		if(tankHost.getHP()<=0) {
	    			clientWon=true;
	    			gc.setFill(Color.RED);
	    			gc.setFont(endMessageFont);
	    			gc.fillText(clientWonString, 600, 600);
	    			
	    		}else if(tankClient.getHP()<=0) {
	    			hostWon=true;
	    			gc.setFill(Color.RED);
	    			gc.setFont(endMessageFont);
	    			gc.fillText(hostWonString, 600, 600);
	    		}
	    		
	        	//Render rate
	    		gc.setFill(Color.WHITE);
	    		gc.setFont(fpsFont);
	            long oldFrameTime = frameTimes[frameTimeIndex];

	            frameTimes[frameTimeIndex] = currentNanoTime ;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = currentNanoTime - oldFrameTime ;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
                    gc.fillText(Double.toString(frameRate),60,50);
                }
                System.out.print("->"+java.lang.Thread.activeCount());
                System.out.println("");
	        }
	    }.start();
		
	}



	
}