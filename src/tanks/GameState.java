package tanks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8658943171528867336L;

	public double hostX;
	public double hostY;
	public double hostDeg;
	public double hostCooldown;
	public double hostHP;
	public double clientX;
	public double clientY;
	public double clientDeg;
	public double clientCooldown;
	public double clientHP;
	List<WallState> wallStateList = new ArrayList<WallState>();
	List<BulletState> bulletStateList = new ArrayList<BulletState>();
	
}
