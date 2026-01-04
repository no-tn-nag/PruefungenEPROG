package aufgabe3;

public class Schwebeumwandlung extends Part {
    @Override
    public void process(Cost c) {
        int temp = c.productionCost;
        c.productionCost *= 1.2;
        c.vat += (c.productionCost - temp) * 10/100;
    }
}
