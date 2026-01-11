package BiomWorldTask;

public class WaterBiom implements Biom{

    private final int flora;

    public WaterBiom(int flora) {
        if (flora < 0) throw new IllegalArgumentException("flora must be >= 0");
        this.flora = flora;
    }

    @Override
    public String getBiomType() {
        return "W";
    }

    @Override
    public int getFlora() {
        return flora;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
