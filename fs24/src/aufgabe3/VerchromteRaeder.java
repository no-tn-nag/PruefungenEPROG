package aufgabe3;

//erst relevant in Teil (b)
public class VerchromteRaeder extends Part {
    @Override
    public void process(Cost c) {
        if (c.productionCost + 7000 > 100000) {
            c.vat += (100000 - c.productionCost) * 7/100;
            c.productionCost = 100000;
        } else {
            c.productionCost += 7000;
            c.vat += 7000 * 7/100;
        }
    }
}
