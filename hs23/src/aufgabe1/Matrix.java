package aufgabe1;

public class Matrix {
	
	public static int[][] kReduce(int[][] input, int k) {
		int m = input.length;
        for (int i = 0; i < m; i++) {
            if (input[i] == null || input[i].length != m) {
                throw new IllegalArgumentException("input is not an m x m matrix");
            }
        }
        if (m == 0 || m % k != 0) {
            throw new IllegalArgumentException("matrix size is not divisible by k");
        }
        int n = m / k;
        int[][] B = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sum = 0;
                int max = Integer.MIN_VALUE;
                int min = Integer.MAX_VALUE;
                for (int row = i * k; row < (i * k) + k; row++) {
                    for (int col = j * k; col < (j * k) + k; col++) {
                        sum += input[row][col];
                        if (input[row][col] > max) max = input[row][col];
                        if (input[row][col] < min) min = input[row][col];
                    }
                }
                if (sum > 0) {
                    B[i][j] = max;
                } else if (sum < 0) {
                    B[i][j] = min;
                } else {
                    B[i][j] = 0;
                }
            }
        }
        return B;
	}
	
}
