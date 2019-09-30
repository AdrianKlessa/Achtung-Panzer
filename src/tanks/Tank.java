package tanks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tank extends Sprite {

	private double rotationSpeed=3;
	private double speed=1;
	private boolean movingForward=false;
	private boolean movingBack=false;
	private Image turretImage;
	private double turretRotationDeg;
	
	public void stop(){
		this.setVelY(0);
		this.setVelX(0);
		movingForward=false;
		movingBack=false;
	}
	
	public void updateTurretRotation(double mouseX, double mouseY) {
		double degX=this.posX-mouseX;
		double degY=this.posY-mouseY;
		
		double ratio=(degX/degY);
		
		double degrees=Math.toDegrees(Math.atan(Math.abs(ratio)));
		//left up
		if(degX>=0&&degY>=0) {
			turretRotationDeg=(360-degrees);

		}
		//left down
		if(degX>=0&&degY<=0) {
			turretRotationDeg=(180+degrees);

		}
		//right up
		if(degX<=0&&degY>=0) {
			turretRotationDeg=degrees;

		}
		//right down
		if(degX<=0&&degY<=0) {
			turretRotationDeg=180-degrees;

		}
		
		
	}
	
	public double getTurretRotationDeg() {
		return turretRotationDeg;
	}
	
	public void turnLeft() {
		this.setRotationDeg(this.getRotationDeg()-rotationSpeed);
		if(movingForward==true) {
			goForward();
		}
		if(movingBack==true) {
			goBack();
		}
	}
	
	
	public void turnRight() {
		this.setRotationDeg(this.getRotationDeg()+rotationSpeed);
		if(movingForward==true) {
			goForward();
		}
		if(movingBack==true) {
			goBack();
		}
	}
	
	public void goForward() {
		this.setVelY(speed*Math.sin(Math.toRadians(this.getRotationDeg()-90)));
		this.setVelX(speed*Math.cos(Math.toRadians(this.getRotationDeg()-90)));
		movingForward=true;
	}
	
	public void goBack() {
		this.setVelY(speed*-0.5*Math.sin(Math.toRadians(this.getRotationDeg()-90)));
		this.setVelX(speed*-0.5*Math.cos(Math.toRadians(this.getRotationDeg()-90)));
		movingBack=true;

	}
	
	public Tank(Image image, Image turretImage, double x, double y, double velX,double velY) {
		super(image, x, y, velX,velY);
		this.turretImage=turretImage;
	}
	
	public Tank(Image image, Image turretImage, double x, double y) {
		super(image,x,y);
		this.turretImage=turretImage;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(rotateImage(image,(int)(rotationDeg)), posX, posY);
		gc.drawImage(rotateImage(turretImage,(int)(turretRotationDeg)), posX, posY);
		
	}
}
