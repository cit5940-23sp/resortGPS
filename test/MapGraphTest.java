import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MapGraphTest {

    @Test
    public void shortestDistancePath() {
    }

    @Test
    public void fewestStopsPath() {
    }

    @Test
    public void getMSTPath() {
        GraphL graph = new GraphL();
        graph.init(4);

        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 2, 15);
        graph.addEdge(1, 3, 11);
        graph.addEdge(2, 3, 4);

        MapGraph mapGraph = new MapGraph(graph);

        int startingAttraction = 0;
        List<Integer> expectedMSTPath = Arrays.asList(0, 3, 2, 1);
        List<Integer> actualMSTPath = mapGraph.getMSTPath(startingAttraction);
        assertEquals(expectedMSTPath, actualMSTPath);

    }

    @Test
    public void roundTripPlanning() {
    }
}