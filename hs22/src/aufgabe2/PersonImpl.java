package aufgabe2;

import java.util.ArrayList;
import java.util.List;

public class PersonImpl implements Person {
    private List<Integer> usedIDs = new ArrayList<>();
    private List<Integer> seenIDs = new ArrayList<>();
    private NotificationType notification = NotificationType.NoNotification;
    boolean positive = false;
    private int age;
    ContactTracer tracer;

    PersonImpl(int age, ContactTracer tracer) {
        this.age = age;
        this.tracer = tracer;
    }

    public int getAge() {
        return age;
    }

    @Override
    public List<Integer> getUsedIds() {
        return usedIDs;
    }

    @Override
    public List<Integer> getSeenIds() {
        return seenIDs;
    }

    @Override
    public NotificationType getNotification() {
        return notification;
    }

    public void setNotificationType(NotificationType notification) {
        this.notification = notification;
    }

    @Override
    public void setTestsPositively() {
        positive = true;
        this.setNotificationType(NotificationType.NoNotification);
        tracer.handlePositiveDiagnosis(this);
    }
}
