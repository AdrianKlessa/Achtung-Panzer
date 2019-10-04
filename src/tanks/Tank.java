package tanks;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tank extends Sprite {

	private double rotationSpeed=3;
	private double speed=1;
	private boolean movingForward=false;
	private boolean movingBack=false;
	private Image turretImage;
	private double turretRotationDeg;
	private double HP=100;
	private boolean isHost;
	private double muzzleVelocity=15;
	private double cooldownMax=100;
	private double currentCooldown=0;
	
	//Ticking the cooldown on firing timer
	public void tickCooldown() {
		if(currentCooldown>0) {
			currentCooldown-=1;
		}
		
	}
	
	public boolean getIsHost() {
		return isHost;
	}
	
	public double getHP() {
		return HP;
	}
	
	public void setHP(double x) {
		HP=x;
	}
	
	public void shoot(double x, double y, List<Bullet> bulletList) {
		if(currentCooldown<=0) {
			double degX=this.posX-x;
			double degY=this.posY-y;
			double sin = Math.sin(Math.toRadians(turretRotationDeg-90));
			double cos = Math.cos(Math.toRadians(turretRotationDeg-90));

			double velX, velY;
			
			velX=cos*muzzleVelocity;
			velY=sin*muzzleVelocity;
			
			Bullet bullet = new Bullet(this.posX,this.posY, velX, velY, isHost);
			bulletList.add(bullet);
			currentCooldown=cooldownMax;
		}

	}
	//Stops tank movement
	public void stop(){
		this.setVelY(0);
		this.setVelX(0);
		movingForward=false;
		movingBack=false;
	}
	
	//Sets the turret rotation based on the vector between it and the mouse cursor
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
	
	public Tank(Image image, Image turretImage, double x, double y, double velX,double velY, boolean isHost) {
		super(image, x, y, velX,velY);
		this.turretImage=turretImage;
		this.isHost=isHost;
	}
	
	public Tank(Image image, Image turretImage, double x, double y, boolean isHost) {
		super(image,x,y);
		this.turretImage=turretImage;
		this.isHost=isHost;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(rotateImage(image,(int)(rotationDeg)), posX, posY);
		gc.drawImage(rotateImage(turretImage,(int)(turretRotationDeg)), posX, posY);
		
	}
	
	public void update(double time, List<Wall> wallList) {
		//Collisions with walls
		boolean collision=false;
		for(int i=0; i<wallList.size();i++) {
			posX+=velX*time;
			posY+=velY*time;
			
			if(this.intersects(wallList.get(i))) {
				collision=true;
				
			}
			posX-=velX*time;
			posY-=velY*time;
		}
		
		
		if(collision==false) {
			
			posX+=velX*time;
			posY+=velY*time;
		}else {
			collision=false;
			while(collision==false) {
				posX+=velX*0.05*time;
				posY+=velY*0.05*time;
				for(int i=0; i<wallList.size();i++) {
					if(this.intersects(wallList.get(i))) {
						collision=true;
						posX-=velX*0.05*time;
						posY-=velY*0.05*time;
					}
				}
			}
		}

	}
	
	public void reduceHP(double amount) {
		HP-=amount;
	}
}
