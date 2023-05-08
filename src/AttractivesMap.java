import java.util.*;

public class AttractivesMap {
    GraphL g;

    List<Integer> suggestionList;


    public AttractivesMap(GraphL g) {
        this.g = g;
    }

    private int nextVertex(HashSet<Integer> nodes, Map<Integer, Integer> shortest) {
        int nextVertex = 0;
        double minDis = Integer.MAX_VALUE;
        for (Integer node: nodes) {
            if (shortest.get(node) < minDis) {
                minDis = shortest.get(node);
                nextVertex = node;
            }
        }

        return nextVertex;
    }



    /**
     * returns the neighbor attractions
     * @param id
     * @return
     */
    public int[] getNeighbors(int id) {
        return this.g.neighbors(id);
    }

    /**
     * Dijkstra's algorithm find out shortest path from a given vertex
     * as the user tours, edges can get updated
     * return a list of most similar attractions
     * @param src
     * @return
     */
    public List<Integer> playSuggestion(int src) {
        Map<Integer, Integer> delEdges = new HashMap<>();
        // Dijkstra's algorithm to find the shortest path
        int nodeCount = this.g.nodeCount();
        //System.out.println(nodeCount);

        Map<Integer, Integer> shortest = new HashMap<>();
        Map<Integer, Integer> pred = new HashMap<>();
        HashSet<Integer> nodes = new HashSet<>();

        // creates shortest and pred array for the attractions
        for (int i = 1; i < nodeCount; i ++) {
            nodes.add(i);
            if (i == src) {
                shortest.put(i, 0);
                pred.put(i, null);
            } else {
                shortest.put(i, Integer.MAX_VALUE);
            }
        }

        int u = src;

        while (nodes.size() != 0) {
            nodes.remove(u);
            for (int v: this.getNeighbors(u)) {
                // remove edge in the other direction
                delEdges.put(v, u);
                this.g.removeEdge(v, u);
                int weight = this.g.weight(u, v);
                if (weight + shortest.get(u) < shortest.get(v)) {
                    shortest.put(v, weight + shortest.get(u));
                    pred.put(v, u);
                }
            }
            u = nextVertex(nodes, shortest);
        }

        for (Map.Entry<Integer, Integer> edge : delEdges.entrySet()) {
            this.g.addEdge(edge.getKey(), edge.getValue(),
                    this.g.weight(edge.getValue(), edge.getKey()));
        }

        List<Map.Entry<Integer, Integer>> scoreList = new ArrayList<>(shortest.entrySet());
        Collections.sort(scoreList, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> e1, Map.Entry<Integer, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        List<Integer> simList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : scoreList) {
            simList.add(entry.getKey());
        }
        this.suggestionList = simList;

        //System.out.println(simList);
        return simList;
    }

    /**
     * generate suggestion list with given count
     * @param count to display
     * @return list of suggestions
     */
    public List<Integer> generateSuggestions(int count, boolean attitude) {
        System.out.println(this.g.nodeCount());
        List<Integer> finalResult = new ArrayList<>();
        if (attitude) {
            // if user likes it
            for (int i = 0; i < count; i++) {
                finalResult.add(this.suggestionList.get(i));
            }
        } else {
            //for (int i = count)
            for (int i = 0; i < count; i++) {
                finalResult.add(this.suggestionList.get(this.g.nodeCount() - 2 - i));
            }
            //finalResult.add(this.suggestionList.get(this.g.nodeCount() - i));

        }

        return finalResult;
    }



}
