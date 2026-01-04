package aufgabe3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Game {

    private final List<Human> humans = new ArrayList<>();
    private final List<ScheduledAction> queue = new ArrayList<>();
	
	public void advanceTurn() {
        List<ScheduledAction> nextQueue = new ArrayList<>();
        for (ScheduledAction sa : queue) {
            if (sa.delay == 0) {
                if (sa.source.isAlive()) {
                    execute(sa);
                }
                sa.source.scheduled = null;
            } else {
                sa.delay -= 1;
                nextQueue.add(sa);
            }
        }

        queue.clear();
        queue.addAll(nextQueue);
	}
	
	public Human createHuman(int health, int position) {
        Human h = new Human(health, position, this);
        humans.add(h);
        return h;
	}
	
	public Human createWarrior(int health, int position) {
        Human w = new Warrior(health, position, this);
        humans.add(w);
        return w;
	}
	
	public Human createCleric(int health, int position) {
        Human c = new Cleric(health, position, this);
        humans.add(c);
        return c;
	}
	
	public Human createGeneral(int health, int position) {
        Human g = new General(health, position, this);
        humans.add(g);
        return g;
	}

    void schedule(Human source, Action action) {
        int delay;
        if (action == Action.ATTACK) {
            delay = 0;
        } else {
            if (source instanceof Warrior) delay = 1;
            else if (source instanceof Cleric) delay = 2;
            else delay = 0;
        }
        ScheduledAction sa = new ScheduledAction(source, action, delay);
        source.scheduled = sa;
        queue.add(sa);
    }

    private void execute(ScheduledAction sa) {
        Human s = sa.source;
        int i = s.getPosition();
        if (sa.action == Action.ATTACK) {
            if (s instanceof Warrior) {
                for (Human h : humans) {
                    if (Math.abs(h.getPosition() - i) == 1) {
                        h.health -= 10;
                    }
                }
            } else if (s instanceof Cleric) {
                for (Human h : humans) {
                    if (Math.abs(h.getPosition() - i) == 1) {
                        h.health -= 3;
                    }
                }
            } else if (s instanceof General) {
                for (Human h : humans) {
                    if (h == s) continue;
                    int j = h.getPosition();
                    int m = countBetween(i, j);
                    int dmg = Math.max(0, 20 - Math.abs(i - j) - m);
                    h.health -= dmg;
                }
            } else {
                // Human
            }
            return;
        }
        if (sa.action == Action.SUMMON) {
            if (s instanceof Warrior) {
                s.health -= 5;
            } else if (s instanceof Cleric) {
                for (Human h : humans) {
                    if (!h.isAlive()) continue;
                    int d = Math.abs(h.getPosition() - i);
                    if (d >= 3 && d <= 5) {
                        h.position = i;
                    }
                }
            } else if (s instanceof General) {
                List<Human> candidates = new ArrayList<>();
                for (Human h : humans) {
                    if (h == s) continue;
                    if (Math.abs(h.getPosition() - i) <= 5) {
                        candidates.add(h);
                    }
                }
                if (candidates.isEmpty()) return;
                candidates.sort(Comparator.comparingInt(h -> h.getHealth() - 10 * Math.abs(i - h.getPosition())));
                int m = candidates.size();
                int idx = (m - 1) / 2;
                Human chosen = candidates.get(idx);
                chosen.position = i;
            } else {
                // Human
            }
        }
    }

    private int countBetween(int a, int b) {
        int lo = Math.min(a, b);
        int hi = Math.max(a, b);
        int count = 0;
        for (Human h : humans) {
            int p = h.getPosition();
            if (p > lo && p < hi) count++;
        }
        return count;
    }

}
