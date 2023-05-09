import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GraphMakerTest {

    @Test
    public void readWeights() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        assertEquals(12, res);
        assertEquals(13, gm.getGraph().nodeCount());
        assertEquals(56, gm.getGraph().weight(1, 2));
        assertEquals(26, gm.getGraph().edgeCount());

        //System.out.println(gm.getGraph().isWheelChairFriendly(2, 4));
        GraphMaker gm2 = new GraphMaker();
        res  = gm2.readWeights("real_map_time_weights.mtx");
        System.out.println(gm2.getGraph().nodeCount());

        GraphMaker gm3 = new GraphMaker();
        res  = gm3.readWeights("real_map_similarity.mtx");
        System.out.println(gm3.getGraph().nodeCount());
    }

    @Test
    public void readCords() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        int res2 = gm.readCords("test_cords.mtx");
        assertEquals(12, res2);
        assertEquals(2, gm.getGraph().getCord(2).getX());
        assertEquals(1, gm.getGraph().getCord(2).getY());
        assertEquals(26, gm.getGraph().edgeCount());

        GraphMaker gm2 = new GraphMaker();
        res  = gm.readWeights("real_map_time_weights.mtx");
        res2 = gm.readCords("real_map_cords.mtx.mtx");
    }

    @Test
    public void readNames() {
        GraphMaker gm = new GraphMaker();
        HashMap<Integer, String> res  = gm.readNames("test_names.mtx");
        assertEquals(3, res.size());
        assertTrue(res.containsKey(1));
        assertTrue(res.containsKey(2));
        assertTrue(res.containsKey(3));

        assertEquals("Good_Ride", res.get(1));
        assertEquals("Beautiful_Coast", res.get(2));
        assertEquals("Thrill_ride", res.get(3));

        res  = gm.readNames("real_map_names.mtx");
        for (Map.Entry<Integer, String> name : res.entrySet()) {
            System.out.println(name.getKey());
            System.out.println(name.getValue());
        }

    }
}