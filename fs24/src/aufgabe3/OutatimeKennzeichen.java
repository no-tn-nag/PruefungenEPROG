package aufgabe3;

public class OutatimeKennzeichen extends Part {
    @Override
    public void process(Cost c) {
        if (c.productionCost + 100 < 50000) {
            c.vat += (50000 - c.productionCost) * 10/100;
            c.productionCost = 50000;
        } else {
            c.vat += 10;
            c.productionCost += 100;
        }
    }
}
