package entities;

/**
 * 
 * Represents a control tower containing a control tower ID and an indicator for
 * activity status.
 *
 */
public class ControlTower {
	private String towerID;
	private boolean isActive;

	public ControlTower(String towerID, boolean isActive) {
		super();
		this.towerID = towerID;
		this.isActive = isActive;
	}

	public String getTowerID() {
		return towerID;
	}

	public void setTowerID(String towerID) {
		this.towerID = towerID;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}