import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MapGraphTest {

    @Test
    public void shortestDistancePath() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        int res2 = gm.readCords("test_cords.mtx");
        MapGraph mg = new MapGraph(gm.getGraph());
        List<Integer> path = mg.shortestDistancePath(1, 3);
        for (int i = 0; i < path.size(); i++) {
            System.out.println("node " + path.get(i));
        }
    }

    @Test
    public void fewestStopsPath() {
    }

    @Test
    public void findAllDestinations() {
    }

    @Test
    public void roundTripPlanning() {
    }
}