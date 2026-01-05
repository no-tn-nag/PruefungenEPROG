package LabelledGraph;

import java.util.Comparator;

public class SortResult implements Comparator<Node> {
    public int compare (Node a, Node b) {
        if (a.pathweight == b.pathweight) {
            return a.getNeighbours().size() - b.getNeighbours().size();
        }
        return a.pathweight - b.pathweight;
    }
}
