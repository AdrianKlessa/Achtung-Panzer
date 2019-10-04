package tanks;

import java.util.List;

public class PlayerUpdater extends Thread{

	private double mouseX;
	private double mouseY;
	private List<Wall> wallList;
	
	Tank host;
	Tank client;
	
	public PlayerUpdater(double mouseX, double mouseY, List<Wall> wallList, Tank host, Tank client) {
		this.mouseX=mouseX;
		this.mouseY=mouseY;
		this.wallList=wallList;
		this.host=host;
		this.client=client;
		
	}
	
	public void run() {
		host.updateTurretRotation(mouseX, mouseY);
		host.update(1, wallList);
		host.tickCooldown();
		client.update(1,wallList);
		client.tickCooldown();
	}
}
