
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SocialNetwork {
    GraphL g;


    public SocialNetwork(GraphL g) {
        this.g = g;

    }

    /**
     * find the number of connected component of a graph
     * i.e. number of friend groups
     * @return the number of connected components
     */
    public int findCluster(){
        System.out.println(g);
        // bfs
        return 0;

    }

    /**
     * A* to sort the friend suggestion list
     * @param src
     * @return
     */
    public List<Integer> friendSuggestion(int src) {
        return new ArrayList<>();

    }

}
