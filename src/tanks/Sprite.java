package tanks;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Sprite {

	protected Image image;
	protected double posX;
	protected double posY;
	protected double velX;
	protected double velY;
	protected double width;
	protected double height;
	protected double rotationDeg;
	
	public double getRotationDeg(){
		return rotationDeg;
	}
	
	public void setRotationDeg(double degrees) {
		this.rotationDeg=degrees;
	}
	
	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	
	public Sprite(Image image, double x, double y, double velX,double velY) {
		this.image=image;
		this.posX=x;
		this.posY=y;
		this.velX=velX;
		this.velY=velY;
		this.width=image.getWidth();
		this.height=image.getHeight();
		
	}
	
	public Sprite(Image image, double x, double y) {
		this.image=image;
		this.posX=x;
		this.posY=y;
		this.width=image.getWidth();
		this.height=image.getHeight();
		
	}
	
	public Sprite() {
		
	}
	//Updating sprite location
	public void update(double time) {
		
		posX+=velX*time;
		posY+=velY*time;
	}
	
	//Rotating the image render by degree
	public Image rotateImage(Image image, int rotation) {
	    ImageView imageView = new ImageView(image);
	    SnapshotParameters params = new SnapshotParameters();
	    params.setFill(Color.TRANSPARENT);
	    params.setTransform(new Rotate(rotation, image.getHeight() / 2, image.getWidth() / 2));
	    params.setViewport(new Rectangle2D(0, 0, image.getHeight(), image.getWidth()));
	    return imageView.snapshot(params, null);
	}
	
	
	//Rendering the image with rotation
	public void render(GraphicsContext gc) {
		
		gc.drawImage(rotateImage(image,(int)(rotationDeg)), posX, posY);
		
	}
	
	public Rectangle2D getBoundary() {
		return new Rectangle2D(posX,posY,width,height);
	}
	
	//Checking for collision with other sprites
	public boolean intersects(Sprite sprite) {
		return sprite.getBoundary().intersects(this.getBoundary());
	}
	
}
