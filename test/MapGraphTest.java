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
        graph.init(5);

        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        AttractivesMap am = new AttractivesMap(gm.getGraph());

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