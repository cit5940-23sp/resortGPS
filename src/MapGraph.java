
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapGraph  {
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

    public List<Integer> findAllDestinations(int src) {
        // Kruskal's
        return new ArrayList<>();
    }

    public List<Integer> roundTripPlanning(int src, int numStops) {
        // dfs
        return new ArrayList<>();

    }
    // DFS to find all attractions that can be visited within the time window
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






}
