package aufgabe3;

public class Human {
	
	public int health, position;
	
	public int getHealth() {
		return health;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean isAlive() {
		return health > 0;
	}
	
	public void scheduleAction(Action action) {
		
	}
}
