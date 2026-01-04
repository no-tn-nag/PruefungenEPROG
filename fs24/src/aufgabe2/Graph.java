package aufgabe2;

import java.util.*;

public class Graph {

    private record Leaf(Node node, Node parent, String incomingLabel, String pathLabel) {
    }


    public static void reverseLeaves(Node root) {
        if (root == null) return;
        List<Leaf> leaves = new ArrayList<>();
        collectLeaves(root, null, null, new StringBuilder(), leaves);
        Map<String, Leaf> byPath = new HashMap<>();
        for (Leaf li : leaves) byPath.put(li.pathLabel, li);
        Set<Node> swapped = new HashSet<>();
        for (Leaf a : leaves) {
            if (swapped.contains(a.node)) continue;
            String rev = reverseString(a.pathLabel);
            Leaf b = byPath.get(rev);
            if (b == null) continue;
            if (swapped.contains(b.node)) continue;

            if (a.parent != null && b.parent != null) {
                a.parent.neighbors.put(a.incomingLabel, b.node);
                b.parent.neighbors.put(b.incomingLabel, a.node);
            } else {
            }
            swapped.add(a.node);
            swapped.add(b.node);
        }
    }

    private static void collectLeaves(Node cur, Node parent, String incomingLabel, StringBuilder path, List<Leaf> out) {
        Map<String, Node> neigh = cur.neighbors;
        boolean isLeaf = (neigh == null || neigh.isEmpty());
        if (isLeaf) {
            out.add(new Leaf(cur, parent, incomingLabel, path.toString()));
            return;
        }
        for (Map.Entry<String, Node> e : neigh.entrySet()) {
            String label = e.getKey();
            Node nxt = e.getValue();
            int oldLen = path.length();
            path.append(label);
            collectLeaves(nxt, cur, label, path, out);
            path.setLength(oldLen);
        }
    }

    private static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static Set<String> findPaths(Node[] roots) {
        Set<String> result = new HashSet<>();
        if (roots == null) return result;
        int n = roots.length;
        List<List<String>> leafLabelsPerTree = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Node r = roots[i];
            List<String> labels = new ArrayList<>();
            if (r != null) collectLeafLabels(r, new StringBuilder(), labels);
            Collections.sort(labels);
            leafLabelsPerTree.add(labels);
        }
        String[] chosen = new String[n];
        backtrackFind(0, leafLabelsPerTree, chosen, result);
        return result;
    }

    private static void collectLeafLabels(Node cur, StringBuilder path, List<String> out) {
        Map<String, Node> neigh = cur.neighbors;
        boolean isLeaf = (neigh == null || neigh.isEmpty());
        if (isLeaf) {
            out.add(path.toString());
            return;
        }
        for (Map.Entry<String, Node> e : neigh.entrySet()) {
            String label = e.getKey();
            Node nxt = e.getValue();
            int oldLen = path.length();
            path.append(label);
            collectLeafLabels(nxt, path, out);
            path.setLength(oldLen);
        }
    }

    private static void backtrackFind(int i, List<List<String>> leafLabelsPerTree, String[] chosen, Set<String> result) {
        int n = leafLabelsPerTree.size();
        if (i == n) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < n; k++) {
                if (k > 0) sb.append('#');
                sb.append(chosen[k]);
            }
            result.add(sb.toString());
            return;
        }
        for (String cand : leafLabelsPerTree.get(i)) {
            if (i >= 2) {
                String prev2 = chosen[i - 2];
                if (cand.compareTo(prev2) < 0) continue;
            }
            chosen[i] = cand;
            backtrackFind(i + 1, leafLabelsPerTree, chosen, result);
        }
    }

}
