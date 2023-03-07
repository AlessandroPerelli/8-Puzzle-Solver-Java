import java.util.Arrays;

public class RunEpuzzleAstar {
    public static void main(String[] arg) {
        
        int seed = 98;
        EpuzzGen gen = new EpuzzGen(seed);

        int d = 6;
        int[][] initialPuzzle = gen.puzzGen(d);
        System.out.println(Arrays.deepToString(initialPuzzle));

        int[][] target = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        EpuzzleSearch searcher = new EpuzzleSearch(target); // creates a search object containing the target state
        SearchState initialState = new EpuzzleState(initialPuzzle, 1, 0); // creates the first node, using the initial state given
        long start = System.nanoTime();
        float searchResults = searcher.runSearchE(initialState, "AStar"); // begins the search, using node object and specified search method
        System.out.println(searchResults); // outputs the results of the search once complete
        long finish = System.nanoTime();
        System.out.println((finish-start)/1000000000);
    }
}
