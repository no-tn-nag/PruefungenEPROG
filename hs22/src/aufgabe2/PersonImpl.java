package aufgabe2;

import java.util.ArrayList;
import java.util.List;

public class PersonImpl implements Person {
    private final int age;
    private final ContactTracer tracer;

    private final List<Integer> usedIds = new ArrayList<>();
    private final List<Integer> seenIds = new ArrayList<>();

    private boolean positive = false;
    private NotificationType notification = NotificationType.NoNotification;

    PersonImpl(int age, ContactTracer tracer) {
        this.age = age;
        this.tracer = tracer;
    }

    int getAge() {
        return age;
    }

    boolean isPositive() {
        return positive;
    }

    @Override
    public List<Integer> getUsedIds() {
        return usedIds;
    }

    @Override
    public List<Integer> getSeenIds() {
        return seenIds;
    }

    @Override
    public NotificationType getNotification() {
        return notification;
    }

    void upgradeNotification(NotificationType newType) {
        if (newType.ordinal() > notification.ordinal()) {
            notification = newType;
        }
    }

    @Override
    public void setTestsPositively() {
        if (positive) return;
        positive = true;
        notification = NotificationType.NoNotification;
        tracer.handlePositiveTest(this);
    }
}
