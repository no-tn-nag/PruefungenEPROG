package aufgabe3;

public class ScheduledAction {
    final Human source;
    final Action action;
    int delay;

    ScheduledAction(Human source, Action action, int delay) {
        this.source = source;
        this.action = action;
        this.delay = delay;
    }
}
