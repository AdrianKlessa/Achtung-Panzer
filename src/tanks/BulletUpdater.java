package tanks;

import java.util.List;

public class BulletUpdater extends Thread{

	private List<Bullet> bulletList;
	private List<Wall> wallList;
	private Tank host;
	private Tank client;
	
	public BulletUpdater(List<Bullet> bulletList, List<Wall> wallList, Tank host, Tank client) {
		this.bulletList=bulletList;
		this.wallList=wallList;
		this.host=host;
		this.client=client;
	}
	
	
	public void run() {
		Bullet bullet;
        for(int i=0;i<bulletList.size();i++) {
        	bullet=bulletList.get(i);
        	
        	if(!bullet.update(1, wallList, bulletList, i, host, client)) {
            	if(bullet.getPosX()<0||bullet.getPosX()>1300||bullet.getPosY()<0||bullet.getPosY()>1300) {
            		//Removing bullets if they go out of bounds
            		bulletList.remove(i);
            	}
        	}

        	
        }
	}
}
