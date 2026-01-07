package aufgabe2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactTracer {

    int nextId = 1;
    List<PersonImpl> persons = new ArrayList<>();

    public ContactTracer() {}
	/**
	 * Erstellt eine neue Person im Kontext dieses Contact Tracers.
	 *
	 * Alle Person Objekte, die von dieser Methode erstellt werden, sollen 
	 * sich gegenseitig begegnen und benachrichtigen können.
	 */
	public Person createPerson(int age) {
        PersonImpl person = new PersonImpl(age, this);
        persons.add(person);
        return person;
	}

    public int getFreshID() {
        return nextId++;
    }

	/**
	 * Protokolliert eine (beidseitige) Begegnung von p1 und p2.
	 */
	public void registerEncounter(Person p1, Person p2) {
        PersonImpl a = (PersonImpl) p1;
        PersonImpl b = (PersonImpl) p2;
        int idA = getFreshID();
        int idB = getFreshID();
        a.getUsedIds().add(idA);
        b.getUsedIds().add(idB);
        a.getSeenIds().add(idB);
        b.getSeenIds().add(idA);
	}


    public void handlePositiveDiagnosis(PersonImpl person) {
        List<PersonImpl> directContact = new ArrayList<>();//direct Contact
        for (PersonImpl p : persons) {
            List<Integer> seen = person.getSeenIds();
            List<Integer> used = p.getUsedIds();
            for (int IdA : seen) {
                for (int IdB : used) {
                    if (IdA == IdB) {
                        directContact.add(p);
                        if (!p.positive) {
                            p.setNotificationType(Person.NotificationType.HighRiskNotification);
                        }
                    }
                }
            }
        }
        //indirect Contact

        for (PersonImpl p : directContact) {
            for (PersonImpl p1 : persons) {
                List<Integer> seen = p.getSeenIds();
                List<Integer> used = p1.getUsedIds();
                for (int IdA : seen) {
                    for (int IdB : used) {
                        if (IdA == IdB) {
                            if (p1.getAge() > 60) {
                                if (!p1.positive) {
                                    p1.setNotificationType(Person.NotificationType.LowRiskNotification);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}