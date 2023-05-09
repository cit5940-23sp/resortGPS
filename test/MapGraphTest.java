import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MapGraphTest {

    @Test
    public void shortestDistancePath() {
    }

    @Test
    public void getMSTPath() {
        GraphMaker physicalGraph = new GraphMaker();
        physicalGraph.readWeights("real_map_dis_weight.mtx");
        physicalGraph.readCords("real_map_cords.mtx");
        MapGraph physicalMap = new MapGraph(physicalGraph.getGraph());
        int startingAttraction = 1;
        List<Integer> mstPath = physicalMap.getMSTPath(startingAttraction);
        assertEquals(mstPath.size(),11);

    }

}