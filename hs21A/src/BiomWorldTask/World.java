package BiomWorldTask;

public class World {

    private int size;
    private Biom[][] grid;

	@Override 
	public String toString() {
    	String str = "";
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
            	str += this.getBiom(x, y).getBiomType();
            }
            str += System.lineSeparator();
        }
        return str;
	}

    public World(String[][] biomGrid) {
        if (biomGrid == null) throw new IllegalArgumentException();
        size = biomGrid.length;

        if (size < 2) throw new IllegalArgumentException();

        grid = new Biom[size][size];

        for (int i = 0; i < size; i++) {
            if (biomGrid[i] == null || biomGrid[i].length != size) {
                throw new IllegalArgumentException();
            }

            for (int j = 0; j < size; j++) {
                String biomRepr = biomGrid[i][j];
                if (biomRepr == null) throw new IllegalArgumentException();

                if (biomRepr.equals("W")) {
                    // Wasser: Flora 15
                    grid[i][j] = new WaterBiom(15);
                } else if (biomRepr.equals("F")) {
                    // Flachland: Flora 12, Höhe 3
                    grid[i][j] = new FlatlandBiom(12, 3);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public Biom getBiom(int x, int y) {
        if (x < 0 || y < 0 || x >= size || y >= size) {
            throw new IllegalArgumentException();
        }
        return grid[x][y];
    }

    public void stepDryUp() {
        Biom[][] old = grid;
        Biom[][] next = new Biom[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Biom b = old[i][j];

                if (b.getBiomType().equals("W")) {
                    int newFlora = Math.max(b.getFlora() - 5, 0);
                    next[i][j] = new WaterBiom(newFlora);
                } else { // "F"
                    int newFlora = Math.max(b.getFlora() - 3, 0);
                    int newHeight = b.getHeight() - 1;

                    if (newHeight <= 0) {
                        // wird Wasser (mit Diversität_neu)
                        next[i][j] = new WaterBiom(newFlora);
                    } else {
                        next[i][j] = new FlatlandBiom(newFlora, newHeight);
                    }
                }
            }
        }

        grid = next;
    }

    public void stepDistribute(int p) {
        if (p != 1 && p != 2) throw new IllegalArgumentException();

        Biom[][] old = grid;
        Biom[][] next = new Biom[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int floraSum = 0;
                int flatlandNeighbors = 0;

                // Nachbarn in Distanz 1..p (nur up/down/left/right)
                for (int k = 1; k <= p; k++) {
                    // up: (i-k, j)
                    if (i - k >= 0) {
                        Biom nb = old[i - k][j];
                        floraSum += nb.getFlora();
                        if (nb.getBiomType().equals("F")) flatlandNeighbors++;
                    }
                    // down: (i+k, j)
                    if (i + k < size) {
                        Biom nb = old[i + k][j];
                        floraSum += nb.getFlora();
                        if (nb.getBiomType().equals("F")) flatlandNeighbors++;
                    }
                    // left: (i, j-k)
                    if (j - k >= 0) {
                        Biom nb = old[i][j - k];
                        floraSum += nb.getFlora();
                        if (nb.getBiomType().equals("F")) flatlandNeighbors++;
                    }
                    // right: (i, j+k)
                    if (j + k < size) {
                        Biom nb = old[i][j + k];
                        floraSum += nb.getFlora();
                        if (nb.getBiomType().equals("F")) flatlandNeighbors++;
                    }
                }

                Biom b = old[i][j];
                if (b.getBiomType().equals("W")) {
                    next[i][j] = new WaterBiom(floraSum);
                } else { // "F"
                    int newHeight = b.getHeight() + flatlandNeighbors;
                    next[i][j] = new FlatlandBiom(floraSum, newHeight);
                }
            }
        }

        grid = next;
    }
}

