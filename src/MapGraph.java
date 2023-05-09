import java.util.*;

public class MapGraph {
    private GraphL g;

    public MapGraph(GraphL g) {
        this.g = g;
    }

    /**
     Returns a list of indices of attractions that are covered by a rectangle defined
     by the upper left corner point (ulX, ulY) and the lower right corner point (lrX, lrY).
     @param ulX The x-coordinate of the upper left corner point of the rectangle.
     @param ulY The y-coordinate of the upper left corner point of the rectangle.
     @param lrX The x-coordinate of the lower right corner point of the rectangle.
     @param lrY The y-coordinate of the lower right corner point of the rectangle.
     @return A list of indices of attractions covered by the given rectangle.
     */
    public List<Integer> covered(int ulX, int ulY, int lrX, int lrY) {

        int size = this.g.nodeCount();
        List<Integer> res = new ArrayList<>();

        for (int i = 1; i < size; i++) {
            Coordinates current = this.g.getCord(i);

            if (current.getX() >= ulX && current.getX() <= lrX) {
                if (current.getY() >= ulY && current.getY() <= lrY) {
                    res.add(i);
                }
            }
        }
        return res;

    }

    /**
     Calculates the heuristic value between two nodes, which is the estimated distance
     between them.
     @param currentNode the current node
     @param destNode the destination node
     @return the heuristic value between the two nodes
     */
    private double heuristic(int currentNode, int destNode) {

        double actualWeight = g.weight(currentNode, destNode);

        Coordinates currentCord = (Coordinates) g.getCord(currentNode);
        Coordinates destCord = (Coordinates) g.getCord(destNode);

        int deltaX = destCord.getX() - currentCord.getX();
        int deltaY = destCord.getY() - currentCord.getY();
        // Euclidean distance
        double eucDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (actualWeight < eucDistance) {
            return actualWeight;
        }
        return eucDistance;
    }


    /**
     * Finds the shortest distance path between two nodes using A* search algorithm.
     *
     * @param src  the source node
     * @param dest the destination node
     * @return a List of Integers representing the path from the source to destination node, or null if no path exists
     */
    // Euclidean Distance
    public List<Integer> shortestDistancePath(int src, int dest) {
        // A*
        int[] cameFrom = new int[g.nodeCount()];
        Map<Integer, Double> gCost = new HashMap<>();
        Map<Integer, Double> fCost = new HashMap<>();

        Comparator<Integer> comparator = (a, b) -> {
            if (fCost.get(a) < fCost.get(b)) {
                return -1;
            } else if (fCost.get(a) > fCost.get(b)) {
                return 1;
            }
            return 0;
        };

        PriorityQueue<Integer> openSet = new PriorityQueue<>(comparator);
        openSet.add(src);

        gCost.put(src, 0.0);
        fCost.put(src, heuristic(src, dest));

        while (!openSet.isEmpty()) {

            int currentNode = openSet.poll();
            // System.out.println("poll from openSet " + currentNode);


            if (currentNode == dest) {
                // Reconstruct path
                List<Integer> path = new ArrayList<>();
                int node = currentNode;
                while (node != src) {
                    path.add(0, node);
                    node = cameFrom[node];
                }
                path.add(0, src);
                return path;
            }

            for (int neighbor : g.neighbors(currentNode)) {
//                System.out.println("neigh " + neighbor);
                double tentativeGCost = gCost.get(currentNode) + g.weight(currentNode, neighbor)/100;
//                System.out.println("gcostW " + tentativeGCost);
                if (!gCost.containsKey(neighbor) || tentativeGCost < gCost.get(neighbor)) {
//                    System.out.println("who enter " + neighbor);
                    cameFrom[neighbor] = currentNode;
                    gCost.put(neighbor, tentativeGCost);
                    fCost.put(neighbor, tentativeGCost + heuristic(neighbor, dest));
//                    System.out.println("who fcost is " + (tentativeGCost + heuristic(neighbor, dest)));

                    if (!openSet.contains(neighbor)) {
//                        System.out.println("within openSet " + neighbor);
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; //

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
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
            // Prioritize wheelchair-friendly paths
            if (o1.isWheelchairFriendly && !o2.isWheelchairFriendly) {
                return -1;
            }
            if (!o1.isWheelchairFriendly && o2.isWheelchairFriendly) {
                return 1;
            }

            // Compare by weight
            return Integer.compare(o1.weight, o2.weight);
        });
        for (int i = 0; i < n; i++) {
            int[] neighbors = g.neighbors(i);
            for (int neighbor : neighbors) {
                int weight = g.weight(i, neighbor);
                boolean isWheelchairFriendly = g.isWheelChairFriendly(i, neighbor);
                pq.add(new Edge(i, neighbor, weight, isWheelchairFriendly));
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

    /**
     Returns the minimum spanning tree path from the given source node using Prim's algorithm.
     The minimum spanning tree is obtained by finding the edges that have the minimum weight
     between nodes until all nodes are visited.
     Then, the MST path is constructed using a depth-first search algorithm.
     @param src the source node to start the MST path from
     @return a list of integers representing the MST path
     */
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

        dfsForMst(src, mstAdjList, mstPath, visited);

        return mstPath;
    }

    /**
     Helper method for performing DFS in the MST to get the path from the source node to all other nodes.
     @param node the current node being visited
     @param adjList the adjacency list representation of the MST
     @param path the list storing the MST path from source node
     @param visited the set of visited nodes in the MST
     */
    private void dfsForMst(int node, Map<Integer, List<Integer>> adjList, List<Integer> path, Set<Integer> visited) {
        path.add(node);
        visited.add(node);

        List<Integer> neighbors = adjList.get(node);
        if (neighbors != null) {
            for (int neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    dfsForMst(neighbor, adjList, path, visited);
                }
            }
        }
    }


    /**
     Helper method for performing DFS on the graph to find all attractions that can be visited within a given time limit.
     @param v the current node being visited
     @param visited the array representing visited nodes
     @param timeLimit the remaining time for the trip
     @param attractions the list storing the visited attractions
     */
    private void DFS(int v, int[] visited, int timeLimit, List<Integer> attractions) {
        visited[v] = 1; // mark current node as visited

        int[] neighbors = this.g.neighbors(v); // get neighbors of current node

        for (int i = 0; i < neighbors.length; i++) {
            int w = neighbors[i];

            if (visited[w] == 0 && this.g.weight(v, w) <= timeLimit) {
                // if neighbor has not been visited and can be visited within time window
                attractions.add(w); // add attraction to list
                DFS(w, visited, timeLimit - this.g.weight(v, w), attractions); // recursively call DFS on neighbor
            }
        }
    }

    // Recommend attractions that can be visited within the time window
    /**
     This method recommends attractions based on a starting attraction and a time limit.
     @param startingAttraction the attraction to start from
     @param timeLimit the maximum amount of time available
     @return a list of recommended attractions that can be visited within the time limit
     */
    public List<Integer> recommendAttractions(int startingAttraction, int timeLimit) {
        // array to keep track of visited nodes
        int[] visited = new int[this.g.nodeCount()];
        // list to store recommended attractions
        List<Integer> attractions = new ArrayList<>();

        // start DFS at the starting attraction
        attractions.add(startingAttraction);
        DFS(startingAttraction, visited, timeLimit, attractions);

        return attractions;
    }



    private class Edge {
        int src, dest, weight;
        boolean isWheelchairFriendly;

        // Constructor
        public Edge(int src, int dest, int weight, boolean isWheelchairFriendly) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
            this.isWheelchairFriendly = isWheelchairFriendly;
        }
    }



}