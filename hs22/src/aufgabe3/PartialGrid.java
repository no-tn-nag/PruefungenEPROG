package aufgabe3;

import java.util.*;

public class PartialGrid {
	
	public static boolean isPartialGrid(Node origin) {
        if (origin == null) return false;
        Map<Node, Coordinate> nodeToCoord = new HashMap<>();
        Set<Coordinate> usedCoords = new HashSet<>();
        Deque<Node> stack = new ArrayDeque<>();
        nodeToCoord.put(origin, new Coordinate(0, 0));
        usedCoords.add(new Coordinate(0, 0));
        stack.push(origin);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            Coordinate c = nodeToCoord.get(current);
            int x = c.getX();
            int y = c.getY();
            Node r = current.getRight();
            if (r != null) {
                Coordinate rc = new Coordinate(x, y + 1);
                if (nodeToCoord.containsKey(r)) {
                    if (!nodeToCoord.get(r).equals(rc)) {
                        return false;
                    }
                } else {
                    if (usedCoords.contains(rc)) {
                        return false;
                    }
                    nodeToCoord.put(r, rc);
                    usedCoords.add(rc);
                    stack.push(r);
                }
            }
            Node d = current.getDown();
            if (d != null) {
                Coordinate dc = new Coordinate(x + 1, y);
                if (nodeToCoord.containsKey(d)) {
                    if (!nodeToCoord.get(d).equals(dc)) {
                        return false;
                    }
                } else {
                    if (usedCoords.contains(dc)) {
                        return false;
                    }
                    nodeToCoord.put(d, dc);
                    usedCoords.add(dc);
                    stack.push(d);
                }
            }
        }

        return true;
	}	
	
	public static boolean isRepresentable(List<Coordinate> coordinates) {
        Set<Coordinate> set = new HashSet<>(coordinates);
        if (!set.contains(new Coordinate(0, 0))) {
            return false;
        }
        for (Coordinate c : set) {
            int x = c.getX();
            int y = c.getY();
            if (x < 0 || y < 0) return false;
            if (x == 0 && y == 0) continue;
            boolean hasUp = set.contains(new Coordinate(x - 1, y));
            boolean hasLeft = set.contains(new Coordinate(x, y - 1));
            if (!hasUp && !hasLeft) {
                return false;
            }
        }
        return true;
	}
}
