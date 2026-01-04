package aufgabe2;

import java.util.*;

public class Matcher {
	public Node start;
	public Set<Node> accepting;
	
	public Matcher(Node n, Set<Node> accepting) {
		this.start = n;
		this.accepting = accepting;
	}
	
	public Set<String> suffix(String w) {
        Map<Node, List<PrevEdge>> rev = new HashMap<>();
        Set<Node> reachable = new HashSet<>();
        dfsBuild(start, reachable, rev);

        Set<Node> goodStarts = new HashSet<>();
        for (Node a : reachable) {
            Node end = follow(a, w);
            if (end != null && accepting.contains(end)) {
                goodStarts.add(a);
            }
        }

        Map<Node, Set<String>> memo = new HashMap<>();
        Set<String> result = new HashSet<>();
        for (Node a : goodStarts) {
            result.addAll(prefixesTo(a, rev, memo));
        }
        return result;
	}

    private void dfsBuild(Node u, Set<Node> visited, Map<Node, List<PrevEdge>> rev) {
        if (!visited.add(u)) return;

        for (Map.Entry<Character, Node> e : u.transitions.entrySet()) {
            char c = e.getKey();
            Node v = e.getValue();

            rev.computeIfAbsent(v, k -> new ArrayList<>()).add(new PrevEdge(u, c));
            dfsBuild(v, visited, rev);
        }
    }

    private Node follow(Node from, String w) {
        Node cur = from;
        for (int i = 0; i < w.length(); i++) {
            cur = cur.transitions.get(w.charAt(i));
            if (cur == null) return null;
        }
        return cur;
    }

    private Set<String> prefixesTo(Node target, Map<Node, List<PrevEdge>> rev, Map<Node, Set<String>> memo) {
        if (memo.containsKey(target)) return memo.get(target);
        Set<String> res = new HashSet<>();
        if (target == start) {
            res.add("");
        } else {
            List<PrevEdge> preds = rev.getOrDefault(target, Collections.emptyList());
            for (PrevEdge pe : preds) {
                for (String p : prefixesTo(pe.prev, rev, memo)) {
                    res.add(p + pe.ch);
                }
            }
        }
        memo.put(target, res);
        return res;
    }


    public String skipmatch(String w, int k) {
		if (w == null) return null;
        if (k < 2 || k > 10) throw new IllegalArgumentException("k is too large or too small");
        Map<String, String> memo = new HashMap<>();
        return dfs(start, w, k, 0, 1, memo);
	}

    private String dfs(Node node, String w, int k, int pos, int step, Map<String, String> memo) {

        String key = System.identityHashCode(node) + "|" + pos + "|" + step;
        if (memo.containsKey(key)) return memo.get(key);
        if (accepting.contains(node) && pos == w.length()) {
            memo.put(key, "");
            return "";
        }
        for (Map.Entry<Character, Node> edge : node.transitions.entrySet()) {
            char label = edge.getKey();
            Node next = edge.getValue();
            if (step == k) {
                String suf = dfs(next, w, k, pos, 1, memo);
                if (suf != null) {
                    String ans = label + suf;
                    memo.put(key, ans);
                    return ans;
                }
            } else {
                if (pos < w.length() && w.charAt(pos) == label) {
                    String suf = dfs(next, w, k, pos + 1, step + 1, memo);
                    if (suf != null) {
                        String ans = label + suf;
                        memo.put(key, ans);
                        return ans;
                    }
                }
            }
        }
        memo.put(key, null);
        return null;
    }
}
