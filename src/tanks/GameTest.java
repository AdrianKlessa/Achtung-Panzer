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
	public GameTest() {
		super(root);
		
		
		
		
		Canvas canvas = new Canvas(1200,800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		ImagePattern background = new ImagePattern(new Image("StoneFloorTexture2.jpg"),0.1,0.1,0.1,0.1,true);
		gc.setFill(background);
		gc.fillRect(0,0,1200,800);
		gc.setFill( Color.RED );
	    gc.setStroke( Color.BLACK );
	    gc.setLineWidth(2);
	    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
	    gc.setFont( theFont );
	    gc.fillText( "Hello, World!", 60, 50 );
	    gc.strokeText( "Hello, World!", 60, 50 );
	    root.getChildren().add(canvas);	    
	    
	    
	    final long startNanoTime = System.nanoTime();
	    Image tankIMG = new Image("tankBase.png");
	    Image wallIMG = new Image("wallTest.jpg");
	    Image tankTurretIMG= new Image("tankTurret.png");
	    Tank tank = new Tank(tankIMG, tankTurretIMG, 500,500);
	    
	    
	    Wall wall;
	    List<Wall> wallList = new ArrayList<Wall>();
	    for(int i=0;i<20;i++) {
	    	wall = new Wall(wallIMG, (double)(i*32),(double)(700));
	    	wallList.add(wall);
	    }
	    
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
	    
	    
	    new AnimationTimer()
	    {
	    	double degX, degY;
	        public void handle(long currentNanoTime)
	        {
	            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	 

	            
	            
	            double x = 232 + 128 * Math.cos(t);
	            double y = 232 + 128 * Math.sin(t);
	 
	            // background image clears canvas
	    		gc.setFill(background);
	    		gc.fillRect(0,0,1200,800);
	    		
	    		
	            for(int i=0;i<wallList.size();i++) {
	            	wallList.get(i).render(gc);
	            }
	            
	    		tank.updateTurretRotation(mouseX, mouseY);
	            tank.render(gc);
	            tank.update(1);

	    		degX=tank.getPosX()-mouseX;
	    		degY=tank.getPosY()-mouseY;
	    		
	        }
	    }.start();
		
	}



	
}
