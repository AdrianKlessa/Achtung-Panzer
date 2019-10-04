package tanks;

import java.util.List;

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
	
	public boolean update(double time, List<Wall> wallList, List<Bullet> bulletList, int index, Tank host, Tank client ) {
		//Collisions with walls; returns true if collided and false if it didn't
		boolean collision=false;
		for(int i=0; i<wallList.size();i++) {
			posX+=velX*time;
			posY+=velY*time;
			
			if(this.intersects(wallList.get(i))&&!collision) {
				collision=true;
				wallList.get(i).reduceHp(damage);
				bulletList.remove(index);
				
			}
			posX-=velX*time;
			posY-=velY*time;
		}
		
		posX+=velX*time;
		posY+=velY*time;
		if(this.intersects(client)) {
			if(belongsToHost) {
				client.reduceHP(damage);
				bulletList.remove(index);
				collision=true;
			}
		}
		
		if(this.intersects(host)) {
			if(!belongsToHost) {
				host.reduceHP(damage);
				bulletList.remove(index);
				collision=true;
			}
		}
		
		//Collisions with tanks
		
		if(collision==false) {
			
			posX+=velX*time;
			posY+=velY*time;
			
		}
		return collision;

	}
}
