import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MapGraphTest {
    @Test
    public void covered() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("real_map_dis_weight.mtx");
        int res2 = gm.readCords("real_map_cords.mtx");
        MapGraph mg = new MapGraph(gm.getGraph());
        List<Integer> resList = mg.covered(30, 50, 100, 100);
        assertEquals(6, resList.size());
        assertTrue(resList.contains(1));
        assertTrue(resList.contains(2));
        assertTrue(resList.contains(3));
        assertTrue(resList.contains(4));


    }

    @Test
    public void shortestDistancePath() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("real_map_dis_weight.mtx");
        int res2 = gm.readCords("real_map_cords.mtx");
        MapGraph mg = new MapGraph(gm.getGraph());
        // path from 1 to 8
        List<Integer> expected18 = Arrays.asList(1, 5, 8);
        List<Integer> path18 = mg.shortestDistancePath(1, 8);
        assertEquals(expected18, path18);

        // path from 3 to 11
        List<Integer> expected311 = Arrays.asList(3, 4, 5, 8, 11);
        List<Integer> path311 = mg.shortestDistancePath(3, 11);
        assertEquals(expected311, path311);

        // path from 0 to 11
        List<Integer> expected111 = Arrays.asList(1, 5, 8, 11);
        List<Integer> path111 = mg.shortestDistancePath(1, 11);
        assertEquals(expected111, path111);

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