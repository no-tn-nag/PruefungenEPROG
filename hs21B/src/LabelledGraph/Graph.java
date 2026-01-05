package LabelledGraph;

import java.util.*;

public class Graph {
	
	public static void removeEdges(Node origin, String label, int maxWeight) {
        if (origin != null && origin.getNeighbours() != null) {
            List<Edge> neighbours = origin.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                removeEdges(neighbours.get(i).getTarget(), label, maxWeight);
                Edge curr = neighbours.get(i);
                if (Objects.equals(curr.getLabel(), label) && curr.getWeight() <= maxWeight) {
                    neighbours.remove(curr);
                }
            }
        }
	}

	public static List<Node> findNodes(Node origin, List<String> path) {
        List<Node> result = new ArrayList<>();
        recHelper(origin, result, path, 0);
        result.sort(new SortResult());
        return result;
	}

    public static void recHelper(Node curr, List<Node> result, List<String> path, int index) {
        for (int i = 0; i < curr.getNeighbours().size(); i++) {
            if (index == path.size() - 1 && Objects.equals(curr.getNeighbours().get(i).getLabel(), path.get(index))
                    && !result.contains(curr.getNeighbours().get(i).getTarget())) {
                result.add(curr.getNeighbours().get(i).getTarget());
            } else if (Objects.equals(curr.getNeighbours().get(i).getLabel(), path.get(index)) && index != path.size() - 1) {
                curr.getNeighbours().get(i).getTarget().pathweight += curr.getNeighbours().get(i).getWeight();
                recHelper(curr.getNeighbours().get(i).getTarget(), result, path, index + 1);
            }
        }
    }
}
