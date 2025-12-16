package aufgabe3;

public class Main {

    public static void main(String[] args) {
        // Sie koennen die main-Methode beliebig aendern.
        
        Game game = new Game();
        Human alice = game.createWarrior(100, 9);
        Human bob = game.createWarrior(100, 10);
        Human casper = game.createWarrior(100, 10);
        Human diana = game.createWarrior(100, 11);
        Human emil = game.createWarrior(100, 12);

        alice.scheduleAction(Action.ATTACK);
        bob.scheduleAction(Action.ATTACK);

        System.out.println("Runde 0...");

        // action is not yet executed
        System.out.println("Alices Gesundheit: " + alice.getHealth());
        System.out.println("Bobs Gesundheit: " + bob.getHealth());
        System.out.println("Caspers Gesundheit: " + casper.getHealth());
        System.out.println("Dianas Gesundheit: " + diana.getHealth());
        System.out.println("Emils Gesundheit: " + emil.getHealth());

        // attacks are executed
        game.advanceTurn();

        System.out.println("\nRunde 1...");
        System.out.println("Alices Gesundheit: " + alice.getHealth());
        System.out.println("Bobs Gesundheit: " + bob.getHealth());
        System.out.println("Caspers Gesundheit: " + casper.getHealth());
        System.out.println("Dianas Gesundheit: " + diana.getHealth());
        System.out.println("Emils Gesundheit: " + emil.getHealth());
    }
}
