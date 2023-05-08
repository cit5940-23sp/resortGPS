import org.junit.Test;

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
    }
}