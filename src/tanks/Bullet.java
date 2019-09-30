package tanks;

import javafx.scene.image.Image;

public class Bullet extends Sprite {

	private double damage=23;
	private static Image bulletImage = new Image("bullet.png");
	private boolean belongsToHost;
	
	public double getDamage() {
		return damage;
	}
	public void setDamage(double value) {
		this.damage=value;
	}
	
	
	public Bullet(double x, double y, double velX, double velY, boolean belongsToHost){
		super(bulletImage, x, y, velX, velY);
		this.belongsToHost=belongsToHost;
	}
	
}
