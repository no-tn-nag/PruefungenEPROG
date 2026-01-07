package aufgabe1;

public class Pattern {

	public static MatchRecord match(int[][] origin, int[][] muster) {
        int k = muster.length;
        int n = origin.length;

        MatchRecord res = new MatchRecord(0, 0, Integer.MAX_VALUE);

        for (int i = 0; i < n - k + 1; i++) {
            for (int j = 0; j < n - k + 1; j++) {
                int wrong = 0;
                for (int l = 0; l < k; l++) {
                    for (int m = 0; m < k; m++) {
                        if (origin[i + l][j + m] != muster[l][m]) {
                            wrong++;
                        }
                    }
                }

                if (wrong < res.count) {
                    res.x = i;
                    res.y = j;
                    res.count = wrong;
                }
            }
        }
        return res;
	}
}
