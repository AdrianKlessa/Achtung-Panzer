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
    
	public GameTest() {
		super(root);
		
		
		
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
	    Tank tank = new Tank(tankIMG, tankTurretIMG, 500,500, true);
	    
	    
	    //Creating walls
	    for(int i=0;i<20;i++) {
	    	wall = new Wall(wallIMG, (double)(i*32),(double)(700));
	    	wallList.add(wall);
	    }
	    
	    //Input
	    this.setOnMouseMoved(e->{
	    	mouseX=e.getSceneX();
	    	mouseY=e.getSceneY();
	    	tank.updateTurretRotation(mouseX, mouseY);
	    	
	    });
	    
	    this.setOnKeyPressed(e->{
	    	if(e.getCode()==KeyCode.LEFT) {
	    		tank.turnLeft();
	    		
	    	}
	    	
	    	if(e.getCode()==KeyCode.RIGHT) {
	    		tank.turnRight();
	    	}
	    	if(e.getCode()==KeyCode.UP) {
	    		
	    		tank.goForward();
	    		
	    	}
	    	if(e.getCode()==KeyCode.DOWN) {
	    		tank.goBack();
	    	}
	    });
	    
	    this.setOnKeyReleased(e->{

	    	if(e.getCode()==KeyCode.UP) {
	    		tank.stop();
	    	}
	    	if(e.getCode()==KeyCode.DOWN) {
	    		tank.stop();
	    	}
	    });
	    
	    this.setOnMousePressed(e->{
	    	mouseX=e.getSceneX();
	    	mouseY=e.getSceneY();
	    	tank.updateTurretRotation(mouseX, mouseY);
	    	tank.shoot(mouseX, mouseY, bulletList);
	    });
	    
	    
	    //Ticking
	    new AnimationTimer()
	    {
	    	double degX, degY;
	        public void handle(long currentNanoTime)
	        {
	        	//Setting up the tickrate
	            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	 

	            
	            
	 
	            // background image clears canvas
	    		gc.setFill(background);
	    		gc.fillRect(0,0,1200,800);
	    		
	    		//Rendering walls
	            for(int i=0;i<wallList.size();i++) {
	            	wallList.get(i).render(gc);
	            }
	            
	            //Rendering bullets
	            for(int i=0;i<bulletList.size();i++) {
	            	bullet=bulletList.get(i);
	            	bullet.render(gc);
	            	bullet.update(1);
	            	if(bullet.getPosX()<0||bullet.getPosX()>1300||bullet.getPosY()<0||bullet.getPosY()>1300) {
	            		//Removing bullets if they go out of bounds
	            		bulletList.remove(i);
	            		System.out.println("Removed bullet");
	            	}
	            	
	            }
	            
	            //Rendering the players
	    		tank.updateTurretRotation(mouseX, mouseY);
	            tank.render(gc);
	            tank.update(1);
	            tank.tickCooldown();
	    		degX=tank.getPosX()-mouseX;
	    		degY=tank.getPosY()-mouseY;
	    		
	        }
	    }.start();
		
	}



	
}
