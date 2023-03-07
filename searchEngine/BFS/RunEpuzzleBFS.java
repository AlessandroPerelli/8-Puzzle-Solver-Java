import java.util.Arrays;

public class RunEpuzzleBFS {
    public static void main(String[] arg) {

        int seed = 98; // determines the initial state generated
        EpuzzGen gen = new EpuzzGen(seed);

        int d = 6; // determines the difficulty of the initial state to solve
        int[][] initialPuzzle = gen.puzzGen(d); // generates a puzzle
        System.out.println(Arrays.deepToString(initialPuzzle));

        int[][] target = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        EpuzzleSearch searcher = new EpuzzleSearch(target); // creates a search object containing the target state
        SearchState initialState = (SearchState) new EpuzzleState(initialPuzzle); // creates the first node, using the initial state given
        long start = System.nanoTime();
        String searchResults = searcher.runSearch(initialState, "breadthFirst"); // begins the search, using node object and specified search method
        System.out.println(searchResults); // outputs the results of the search once complete
        long finish = System.nanoTime();
        System.out.println((finish-start)/1000000000); // outputs the runtime in seconds
    }
}
