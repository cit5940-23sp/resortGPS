import java.util.*;

/**
 * Constructs a TrafficMap object with the given graph.
 */
public class TrafficMap {
    GraphL g;

    List<Integer> suggestionList;


    public TrafficMap(GraphL g) {
        this.g = g;
    }

    /**
     Helper method to find the next vertex in the Dijkstra's algorithm for generating suggestions.
     Given the set of nodes and their shortest distances,
     it returns the node with the minimum distance
     from the starting vertex.
     @param nodes A set of nodes.
     @param shortest A map with the shortest distances of each node from the starting vertex.
     @return The next vertex to visit according to Dijkstra's algorithm.
     */
    private int nextVertex(HashSet<Integer> nodes, Map<Integer, Integer> shortest) {
        int nextVertex = 0;
        double minDis = Integer.MAX_VALUE;
        for (Integer node : nodes) {
            if (shortest.get(node) < minDis) {
                minDis = shortest.get(node);
                nextVertex = node;
            }

        }

        return nextVertex;
    }


    /**
     * returns the neighbor attractions
     *
     * @param id node
     * @return list of neighbors
     */
    public int[] getNeighbors(int id) {
        return this.g.neighbors(id);
    }

    /**
     * Dijkstra's algorithm find out the shortest path from a given vertex
     * as the user tours, edges can get updated
     * return a list of most similar attractions
     *
     * @param src source
     * @return simList
     */
    public List<Integer> generateSuggestions(int src) {
        Map<Integer, Integer> delEdges = new HashMap<>();
        int nodeCount = this.g.nodeCount();

        Map<Integer, Integer> shortest = new HashMap<>();
        Map<Integer, Integer> pred = new HashMap<>();
        HashSet<Integer> nodes = new HashSet<>();

        // creates shortest and pred array for the attractions
        for (int i = 1; i < nodeCount; i++) {
            nodes.add(i);
            if (i == src) {
                shortest.put(i, 0);
                pred.put(i, null);
            } else {
                shortest.put(i, Integer.MAX_VALUE);
            }
        }

        int u = src;
        nodes.remove(1);

        while (nodes.size() != 0) {
            nodes.remove(u);
            for (int v : this.getNeighbors(u)) {
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
        scoreList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        List<Integer> simList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : scoreList) {
            simList.add(entry.getKey());
        }
        this.suggestionList = simList;

        return simList;
    }


}