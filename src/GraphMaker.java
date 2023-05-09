import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphMaker implements IGraphMaker {

    private int numOfVer;

    private GraphL g;

    private HashMap<Integer, String> attractionNames = new HashMap<>();

    /**
     * Reads the weights of the graph from a file and constructs a GraphL object.
     *
     * @param filePath the file path of the file containing the weights of the graph
     * @return the number of vertices in the graph
     * @throws RuntimeException if an I/O error occurs
     */
    @Override
    public int readWeights(String filePath) {
        FileReader fileReader = null;
        int numOfNode = 0;

        try {
            fileReader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line = buffReader.readLine();
            String[] arrOfStr = line.split(" ", 2);
            numOfNode = Integer.valueOf(arrOfStr[0]);
            // construct the graph
            GraphL g = new GraphL();
            g.init(numOfNode + 1);

            // fill in vertices and edges
            while (buffReader.ready()) {
                line = buffReader.readLine();
                String[] edgeWeight = line.split(" ", 4);
                int v = Integer.valueOf(edgeWeight[0]);
                int w = Integer.valueOf(edgeWeight[1]);
                double weightFloat = Double.valueOf(edgeWeight[2]);

                // only add edge beyond a threshold

                int weight = (int)(100 * weightFloat);
                // undirected graph
                g.addEdge(v, w, weight);
                g.addEdge(w, v, weight);


                int isWheel = Integer.valueOf(edgeWeight[3]);
                if (isWheel == 0) {
                    // false
                    g.setWheelChairFriendly(v, w, false);
                } else {
                    // true
                    g.setWheelChairFriendly(v, w, true);
                }


            }

            this.g = g;
            this.numOfVer = numOfNode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.numOfVer;
    }
    /**
     * Reads the coordinates of the vertices in the graph from a file and sets the coordinates for each vertex.
     *
     * @param filePath the file path of the file containing the coordinates of the vertices in the graph
     * @return the number of vertices in the graph
     * @throws RuntimeException if an I/O error occurs
     */
    @Override
    public int readCords(String filePath) {
        FileReader fileReader = null;
        int res = 0;

        try {
            fileReader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line = buffReader.readLine();
            String[] arrOfStr = line.split(" ", 1);
            res = Integer.valueOf(arrOfStr[0]);
            // construct the graph

            // fill in vertices and edges
            while (buffReader.ready()) {
                line = buffReader.readLine();
                String[] cords = line.split(" ", 3);
                int index = Integer.valueOf(cords[0]);
                int x = Integer.valueOf(cords[1]);
                int y = Integer.valueOf(cords[2]);

                // only add edge beyond a threshold
                Coordinates cor = new Coordinates(x, y);
                this.g.setCord(index, cor);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }



    /**
     * Reads attraction names from a file and stores them in a HashMap with node indices as keys and names as values.
     * The file should contain the number of nodes on the first line, followed by one line per node containing the index
     * of the node and its corresponding name separated by a space.
     *
     * @param filePath the path of the file containing the attraction names
     * @return a HashMap<Integer, String> containing the attraction names
     * @throws RuntimeException if there is an error reading the file
     */
    public HashMap<Integer, String> readNames(String filePath) {
        FileReader fileReader = null;
        int res = 0;

        try {
            fileReader = new FileReader(filePath);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line = buffReader.readLine();
            String[] arrOfStr = line.split(" ", 1);
            res = Integer.valueOf(arrOfStr[0]);
            // construct the graph

            // fill in vertices and edges
            while (buffReader.ready()) {
                line = buffReader.readLine();
                String[] cords = line.split(" ", 2);
                int node = Integer.valueOf(cords[0]);
                String name = cords[1];
                this.attractionNames.put(node, name);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.attractionNames;
    }

    /**

     Returns the GraphL instance that was constructed by the readWeights and readCords methods.
     @return GraphL instance representing the graph with vertices and edges.
     */
    public GraphL getGraph() {
        return this.g;
    }
}