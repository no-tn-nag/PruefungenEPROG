package aufgabe1;

public class Pattern {

	public static MatchRecord match(int[][] origin, int[][] muster) {
        int n = origin.length;
        int k = muster.length;
        if (k > n) {
            throw new IllegalArgumentException("muster (k) must not be larger than origin (n)");
        }
        int bestCount = Integer.MAX_VALUE;
        int bestX = 0;
        int bestY = 0;
        for (int x = 0; x <= n - k; x++) {
            for (int y = 0; y <= n - k; y++) {
                int mismatches = 0;
                for (int i = 0; i < k; i++) {
                    for (int j = 0; j < k; j++) {
                        if (origin[x + i][y + j] != muster[i][j]) {
                            mismatches++;
                            if (mismatches >= bestCount) {
                                i = k;
                                break;
                            }
                        }
                    }
                }
                if (mismatches < bestCount) {
                    bestCount = mismatches;
                    bestX = x;
                    bestY = y;
                    if (bestCount == 0) {
                        return new MatchRecord(bestX, bestY, bestCount);
                    }
                }
            }
        }
        return new MatchRecord(bestX, bestY, bestCount);
	}
}
