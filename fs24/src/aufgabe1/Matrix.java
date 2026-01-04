package aufgabe1;

public class Matrix {

    public static int countAssimilated(int[][] matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null");
        }
        int m = matrix.length;
        if (m <= 2) {
            throw new IllegalArgumentException("m must be > 2");
        }
        if (matrix[0] == null) {
            throw new IllegalArgumentException("matrix has null row");
        }
        int n = matrix[0].length;
        if (n <= 2) {
            throw new IllegalArgumentException("n must be > 2");
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i] == null || matrix[i].length != n) {
                throw new IllegalArgumentException("matrix must be rectangular (m x n) with no null rows");
            }
        }
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) continue;
                        int ni = i + di;
                        int nj = j + dj;
                        if (0 <= ni && ni < m && 0 <= nj && nj < n) {
                            sum += matrix[ni][nj];
                        }
                    }
                }
                if (sum % matrix[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("countAssimilated([[10, 10, 10], [10, 10, 10], [10, 10, 10]]): " +
                countAssimilated(new int[][]{{10, 10, 10}, {10, 10, 10}, {10, 10, 10}}));
        System.out.println("countAssimilated([[5, 10, 3], [6, 9, 6], [3, 3, 15]]): " +
                countAssimilated(new int[][]{{5, 10, 3}, {6, 9, 6}, {3, 3, 15}}));
        System.out.println("countAssimilated([[4, 7, 13], [-2, -12, 32], [20, 15, -8], [17, 3, 1111]]): " +
                countAssimilated(new int[][]{{4, 7, 13}, {-2, -12, 32}, {20, 15, -8}, {17, 3, 1111}}));
        System.out.println("countAssimilated([[1, 2, 3, 4, 5, 6], " +
                "[7, 8, 9, 10, 11, 12], " +
                "[13, 14, 15, 16, 17, 18], " +
                "[19, 20, 21, 22, 23, 24], " +
                "[25, 26, 27, 28, 29, 30]]): " +
                countAssimilated(new int[][]{{1, 2, 3, 4, 5, 6},
                        {7, 8, 9, 10, 11, 12},
                        {13, 14, 15, 16, 17, 18},
                        {19, 20, 21, 22, 23, 24},
                        {25, 26, 27, 28, 29, 30}}));
    }
}
