package PagerTask;

public class Main {
	
	public static void main(String[] args) {
		// Sie koennen die main Methode beliebig aendern.

		Hospital h = new Hospital();
		Pager anna = h.createPager("normal");
		anna.register("Anna");
		Pager bernd = h.createPager("normal");
		bernd.register("Bernd");
		
		// Am Anfang sind beide Inboxen leer
		System.out.println(anna.inbox().size());
		System.out.println(bernd.inbox().size());
		
		anna.command("Bernd", new TextMessage("Hello World"));
		
		// Bernd's Pager hat jetzt eine Nachricht
		System.out.println(anna.inbox().size());
		System.out.println(bernd.inbox().size());
	}

}
