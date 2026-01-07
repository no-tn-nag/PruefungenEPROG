package aufgabe3;

import java.util.*;

public class PartialGrid {
	
	public static boolean isPartialGrid(Node origin) {
        Node[][] grid = new Node[10000][10000];
        boolean right = false;
        boolean down = false;
        if (origin != null) {
            grid[0][0] = origin;
            right = recHelper(origin.getRight(), 0, 1, grid);
            down = recHelper(origin.getDown(), 1, 0, grid);
        }
        return right && down;
	}

    public static boolean recHelper (Node current, int i, int j, Node[][] grid) {
        if (grid[i][j] != null) {
            return false;
        } else {
            grid[i][j] = current;
            boolean right = false;
            boolean down = false;
            if (current != null) {
                right = recHelper(current.getRight(), i, j + 1, grid);
                down = recHelper(current.getDown(), i + 1, j, grid);
            }
            return right && down;
        }
    }
    //VERSTEHE ICH NICHT!!!!

	public static boolean isRepresentable(List<Coordinate> coordinates) {
        return false;
	}
}
