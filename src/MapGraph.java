
import java.util.*;

public class MapGraph {
    private Graph g;

    public MapGraph(GraphL g) {
        this.g = g;
    }


    public List<Integer> shortestDistancePath(int src, int dest, int stop) {
        // A*
        return new ArrayList<>();
    }

    public List<Integer> fewestStopsPath(int src, int dest) {
        // BFS
        return new ArrayList<>();
    }

    /**
     * The tourists receive a list of attractions connected by the shortest
     * path that minimizes their walking distance throughout the amusement park.
     * This allows them to spend less time walking and more time enjoying the attractions.
     *
     * @param src
     * @return
     */
    public List<Edge> findAllDestinations(int src) {
        int n = g.nodeCount();
        UnionFind uf = new UnionFind(n);

        // Step 1: Sort all the edges in the graph in non-decreasing order of their weights.
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        for (int i = 0; i < n; i++) {
            int[] neighbors = g.neighbors(i);
            for (int neighbor : neighbors) {
                int weight = g.weight(i, neighbor);
                pq.add(new Edge(i, neighbor, weight));
            }
        }

        // Step 2 & 3: Create MST using Kruskal's algorithm
        List<Edge> mstEdges = new ArrayList<>();
        int edgesAdded = 0;
        while (!pq.isEmpty() && edgesAdded < n - 1) {
            Edge edge = pq.poll();
            if (uf.union(edge.src, edge.dest)) {
                mstEdges.add(edge);
                edgesAdded++;
            }
        }
        return mstEdges;
    }

    public List<Integer> getMSTPath(int src) {
        List<Edge> mstEdges = findAllDestinations(src);
        Map<Integer, List<Integer>> mstAdjList = new HashMap<>();

        for (Edge edge : mstEdges) {
            mstAdjList.putIfAbsent(edge.src, new ArrayList<>());
            mstAdjList.putIfAbsent(edge.dest, new ArrayList<>());
            mstAdjList.get(edge.src).add(edge.dest);
            mstAdjList.get(edge.dest).add(edge.src);
        }

        List<Integer> mstPath = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        dfs(src, mstAdjList, mstPath, visited);

        return mstPath;
    }

    private void dfs(int node, Map<Integer, List<Integer>> adjList, List<Integer> path, Set<Integer> visited) {
        path.add(node);
        visited.add(node);

        List<Integer> neighbors = adjList.get(node);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor, adjList, path, visited);
                }
            }
        }
    }



    private class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public List<Integer> roundTripPlanning(int src) {
        // dfs
        return new ArrayList<>();

    }


}
