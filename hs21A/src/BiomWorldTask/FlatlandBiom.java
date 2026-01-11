package BiomWorldTask;

public class FlatlandBiom implements Biom {

    private final int flora;
    private final int height;

    public FlatlandBiom(int flora, int height) {
        if (flora < 0) throw new IllegalArgumentException("flora must be >= 0");
        if (height <= 0) throw new IllegalArgumentException("height must be > 0");
        this.flora = flora;
        this.height = height;
    }

    @Override
    public String getBiomType() {
        return "F";
    }

    @Override
    public int getFlora() {
        return flora;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
