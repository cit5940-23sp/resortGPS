
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MapGraph  {
    private Graph g;

    public MapGraph(GraphL g) {
        this.g = g;
    }

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
            System.out.println("poll from openSet " + currentNode);


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
                System.out.println("neigh " + neighbor);
                double tentativeGCost = gCost.get(currentNode) + g.weight(currentNode, neighbor);
                System.out.println("gcostW " + tentativeGCost);
                if (!gCost.containsKey(neighbor) || tentativeGCost < gCost.get(neighbor)) {
                    System.out.println("who enter " + neighbor);
                    cameFrom[neighbor] = currentNode;
                    gCost.put(neighbor, tentativeGCost);
                    fCost.put(neighbor, tentativeGCost + heuristic(neighbor, dest));
                    System.out.println("who fcost is " + (tentativeGCost + heuristic(neighbor, dest)));

                    if (!openSet.contains(neighbor)) {
                        System.out.println("within openSet " + neighbor);
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; //

    }

    public List<Integer> fewestStopsPath(int src, int dest) {
        // BFS
        return new ArrayList<>();
    }

    public List<Integer> findAllDestinations(int src) {
        // Kruskal's
        return new ArrayList<>();
    }

    public List<Integer> roundTripPlanning(int src) {
        // dfs
        return new ArrayList<>();

    }




}
