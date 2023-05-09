import org.junit.Test;

import java.util.List;

public class AttractivesTest {

    @Test
    public void playSuggestion() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        AttractivesMap am = new AttractivesMap(gm.getGraph());
        List<Integer> suggestionList = am.playSuggestion(1);
        System.out.println(suggestionList);
        suggestionList = am.playSuggestion(3);
        System.out.println(suggestionList);

        suggestionList = am.playSuggestion(8);
        System.out.println(suggestionList);
    }

    @Test
    public void generateSuggestions() {
        GraphMaker gm = new GraphMaker();
        int res  = gm.readWeights("test_weight.mtx");
        AttractivesMap am = new AttractivesMap(gm.getGraph());
        List<Integer> suggestionList = am.playSuggestion(1);
        System.out.println(suggestionList);

        List<Integer> finalRes = am.generateSuggestions(5, false);
        System.out.println(finalRes);
        finalRes = am.generateSuggestions(5, true);
        System.out.println(finalRes);

        suggestionList = am.playSuggestion(10);
        finalRes = am.generateSuggestions(3, true);
        System.out.println(finalRes);

        GraphMaker gm2 = new GraphMaker();
        int res2  = gm.readWeights("real_map_similarity.mtx");
        AttractivesMap am2 = new AttractivesMap(gm.getGraph());
        List<Integer> suggestionList2 = am.playSuggestion(1);
        System.out.println(suggestionList);
    }

}