import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TrafficMapTest {

    @Test
    public void generateSuggestions() {
        GraphMaker gm2 = new GraphMaker();
        int res2  = gm2.readWeights("real_map_traffic.mtx");
        TrafficMap am2 = new TrafficMap(gm2.getGraph());
        List<Integer> res = am2.generateSuggestions(11);
        assertEquals(11, res.size());
        assertEquals(7, (int) res.get(2));
        assertEquals(1, (int) res.get(0));
        assertEquals(10, (int) res.get(1));
    }
}