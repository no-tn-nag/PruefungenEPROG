package aufgabe3;

public class Fluegeltueren extends Part {
    @Override
    public void process(Cost c) {
        c.productionCost += 2000;
        c.vat += 2000 * 3/100;
    }
}
