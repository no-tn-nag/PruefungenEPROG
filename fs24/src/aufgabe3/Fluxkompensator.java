package aufgabe3;

public class Fluxkompensator extends Part {
    @Override
    public void process(Cost c) {
        c.vat += c.productionCost * 7/100;
        c.productionCost *= 2;
    }
}
