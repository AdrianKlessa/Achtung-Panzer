package tanks;

import javafx.scene.image.Image;

public class Wall extends Sprite{

	private double hp=100;
	
	public Wall(Image image, double x, double y) {
		super(image,x,y);
	}
	public void reduceHp(double amount) {
		this.hp-=amount;
	}
	public double getHp() {
		return this.hp;
	}
}
