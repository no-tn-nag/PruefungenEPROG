package aufgabe3;

//erst relevant in Teil (b)
public class FirstEditionFluxkompensator extends Part {
    @Override
    public void process(Cost c) {
        c.vat += c.productionCost * 7/100;
        c.productionCost *= 2;
    }
}
