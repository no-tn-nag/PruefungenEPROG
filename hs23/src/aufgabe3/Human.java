package aufgabe3;

public class Human {

    public int health, position;
    Game game;
    ScheduledAction scheduled;

    Human(int health, int position, Game game) {
        this.health = health;
        this.position = position;
        this.game = game;
    }


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
        if (!isAlive()) {
            throw new IllegalArgumentException("Dead human cannot schedule actions");
        }
        if (scheduled != null) {
            throw new IllegalArgumentException("Human already has a scheduled action");
        }
        game.schedule(this, action);
	}
}
