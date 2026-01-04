package aufgabe2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactTracer {
	/**
	 * Erstellt eine neue Person im Kontext dieses Contact Tracers.
	 *
	 * Alle Person Objekte, die von dieser Methode erstellt werden, sollen 
	 * sich gegenseitig begegnen und benachrichtigen können.
	 */

    private int nextId = 1;
    private final List<PersonImpl> persons = new ArrayList<>();

    public ContactTracer() {}

    private int freshId() {
        return nextId++;
    }
	public Person createPerson(int age) {
        PersonImpl p = new PersonImpl(age, this);
        persons.add(p);
        return p;
	}

	/**
	 * Protokolliert eine (beidseitige) Begegnung von p1 und p2.
	 */
	public void registerEncounter(Person p1, Person p2) {
        PersonImpl a = (PersonImpl) p1;
        PersonImpl b = (PersonImpl) p2;
        int idA = freshId();
        int idB = freshId();
        a.getUsedIds().add(idA);
        b.getSeenIds().add(idA);
        b.getUsedIds().add(idB);
        a.getSeenIds().add(idB);
	}

    public void handlePositiveTest(PersonImpl infected) {

        Set<PersonImpl> directContacts = new HashSet<>();
        for (PersonImpl p : persons) {
            if (p == infected) continue;

            for (int id : infected.getUsedIds()) {
                if (p.getSeenIds().contains(id)) {
                    directContacts.add(p);
                }
            }
        }
        for (PersonImpl p : directContacts) {
            if (p.isPositive()) continue;
            p.upgradeNotification(Person.NotificationType.HighRiskNotification);
        }
        for (PersonImpl mid : directContacts) {
            for (PersonImpl p : persons) {
                if (p == infected || p == mid || p.isPositive()) continue;
                boolean indirect = false;
                for (int id : mid.getUsedIds()) {
                    if (p.getSeenIds().contains(id)) {
                        indirect = true;
                        break;
                    }
                }
                if (indirect) {
                    if (p.getAge() > 60) {
                        p.upgradeNotification(Person.NotificationType.LowRiskNotification);
                    }
                }
            }
        }
    }
	
}