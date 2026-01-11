package LabyrinthTask;

import java.util.*;

public class Labyrinth {
	
	public static List<Room> exits(Room room, List<Room> prefix) {
        if (room == null || prefix == null) {
            throw new IllegalArgumentException("room/prefix must not be null");
        }

        Room start = room;

        if (!prefix.isEmpty()) {
            if (prefix.get(0) != room) {
                return new LinkedList<>(); // kein gültiges Präfix ab room
            }
            // prüfen, ob prefix ein gültiger Pfad ist (jeweils Tür zum nächsten)
            for (int i = 0; i < prefix.size() - 1; i++) {
                Room cur = prefix.get(i);
                Room nxt = prefix.get(i + 1);
                if (!hasDoorTo(cur, nxt)) {
                    return new LinkedList<>();
                }
            }
            start = prefix.get(prefix.size() - 1);
        }

        // Sammle alle erreichbaren Ausgänge (distinct) ab start
        LinkedHashSet<Room> exits = new LinkedHashSet<>();
        collectReachableExits(start, exits);
        return new LinkedList<>(exits);
	}

    private static boolean hasDoorTo(Room from, Room to) {
        for (Room x : from.doorsTo) {
            if (x == to) return true;
        }
        return false;
    }

    private static void collectReachableExits(Room r, LinkedHashSet<Room> out) {
        // DFS (azyklisch, aber wir schützen uns trotzdem mit visited)
        Set<Room> visited = new HashSet<>();
        Deque<Room> stack = new ArrayDeque<>();
        stack.push(r);

        while (!stack.isEmpty()) {
            Room cur = stack.pop();
            if (!visited.add(cur)) continue;

            if (cur.isExit()) {
                out.add(cur);
            } else {
                // Reihenfolge: wie doorsTo gegeben
                for (int i = cur.doorsTo.length - 1; i >= 0; i--) {
                    stack.push(cur.doorsTo[i]);
                }
            }
        }
    }
	
	public static void sortRooms(List<Room> a) {
        if (a == null) {
            throw new IllegalArgumentException("list must not be null");
        }

        // Memo für Exit-Infos (damit wir pro Room nicht zig-mal rechnen)
        Map<Room, ExitInfo> memo = new HashMap<>();

        Comparator<Room> cmp = (r1, r2) -> {
            ExitInfo e1 = exitInfo(r1, memo);
            ExitInfo e2 = exitInfo(r2, memo);

            if (e1.exitCount < e2.exitCount) return -1;
            if (e1.exitCount > e2.exitCount) return 1;

            // exitCount gleich: 2. Regel
            // r1 vor r2, falls maxLen(r1) > maxLen(r2)
            if (e1.maxExitNameLen > e2.maxExitNameLen) return -1;
            if (e1.maxExitNameLen < e2.maxExitNameLen) return 1;

            // keine Bedingung erzwingt eine Reihenfolge
            return 0;
        };

        a.sort(cmp);
	}

    private static class ExitInfo {
        final Set<Room> exits;      // distinct exits (identity)
        final int exitCount;
        final int maxExitNameLen;

        ExitInfo(Set<Room> exits, int maxExitNameLen) {
            this.exits = exits;
            this.exitCount = exits.size();
            this.maxExitNameLen = maxExitNameLen;
        }
    }

    private static ExitInfo exitInfo(Room r, Map<Room, ExitInfo> memo) {
        ExitInfo cached = memo.get(r);
        if (cached != null) return cached;

        if (r.isExit()) {
            Set<Room> s = new HashSet<>();
            s.add(r);
            ExitInfo info = new ExitInfo(s, r.getName().length());
            memo.put(r, info);
            return info;
        }

        Set<Room> union = new HashSet<>();
        int maxLen = 0;

        for (Room nxt : r.doorsTo) {
            ExitInfo child = exitInfo(nxt, memo);
            union.addAll(child.exits);
        }
        for (Room ex : union) {
            maxLen = Math.max(maxLen, ex.getName().length());
        }

        ExitInfo info = new ExitInfo(union, maxLen);
        memo.put(r, info);
        return info;
    }


    public static boolean pathsWithSameNames(Room room, int n) {
        if (room == null) {
            throw new IllegalArgumentException("room must not be null");
        }
        if (n <= 0) return true; // "mindestens 0" ist immer erfüllbar

        // memo: pro Room eine Map signature -> count (gedeckelt auf n)
        Map<Room, Map<String, Integer>> memo = new HashMap<>();
        Map<String, Integer> sigCounts = pathSignatureCounts(room, n, memo);

        for (int cnt : sigCounts.values()) {
            if (cnt >= n) return true;
        }
        return false;
	}

    private static Map<String, Integer> pathSignatureCounts(Room r, int cap, Map<Room, Map<String, Integer>> memo) {
        Map<String, Integer> cached = memo.get(r);
        if (cached != null) return cached;

        Map<String, Integer> res = new HashMap<>();

        if (r.isExit()) {
            // Signatur: Multiset nur aus diesem Namen
            String sig = canonicalSigWithAddedName("", r.getName());
            res.put(sig, 1);
            memo.put(r, res);
            return res;
        }

        for (Room nxt : r.doorsTo) {
            Map<String, Integer> childMap = pathSignatureCounts(nxt, cap, memo);

            for (Map.Entry<String, Integer> e : childMap.entrySet()) {
                String childSig = e.getKey();
                int childCnt = e.getValue();

                String newSig = canonicalSigWithAddedName(childSig, r.getName());

                int cur = res.getOrDefault(newSig, 0);
                int add = childCnt;

                // distinct paths addieren, aber auf cap deckeln
                int newVal = cur + add;
                if (newVal > cap) newVal = cap;

                res.put(newSig, newVal);
            }
        }

        memo.put(r, res);
        return res;
    }

    private static String canonicalSigWithAddedName(String sig, String nameToAdd) {
        TreeMap<String, Integer> multiset = new TreeMap<>();

        if (sig != null && !sig.isEmpty()) {
            String[] parts = sig.split("\\|");
            for (String p : parts) {
                if (p.isEmpty()) continue;
                String[] kv = p.split("#", 2);
                String nm = kv[0];
                int cnt = Integer.parseInt(kv[1]);
                multiset.put(nm, cnt);
            }
        }

        multiset.put(nameToAdd, multiset.getOrDefault(nameToAdd, 0) + 1);

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Integer> e : multiset.entrySet()) {
            if (!first) sb.append("|");
            first = false;
            sb.append(e.getKey()).append("#").append(e.getValue());
        }
        return sb.toString();
    }



    public static boolean sameNames(List<Room> a, List<Room> b) {
		Map<String, Integer> ma = new HashMap<>();
		for (Room x : a) {
			String n = x.getName();
			ma.put(n, ma.getOrDefault(n, 0)+1);
		}
		
		Map<String, Integer> mb = new HashMap<>();
		for (Room x : b) {
			String n = x.getName();
			mb.put(n, mb.getOrDefault(n, 0)+1);
		}
		
		if (!ma.keySet().equals(mb.keySet())) {
			return false;
		}
		
		for (String n : ma.keySet()) {
			if (!ma.get(n).equals(mb.get(n))) {
				return false;
			}
		}
		
		return true;
	}
	
}
