import java.util.ArrayList;
import java.util.Arrays;

public class EpuzzleState extends SearchState {
    private int [][] initialState;

    public EpuzzleState(int[][] givenState) {
        initialState = givenState;
    }

    public boolean goalPredicate(Search searcher) {
        EpuzzleSearch puzzleSearcher = (EpuzzleSearch) searcher;
        int [][] target = puzzleSearcher.getTarget(); // gets the target state from EpuzzleSearch class
        return Arrays.deepEquals(initialState, target); // checks if the node state equals the target state
    }

    public ArrayList<SearchState> getSuccessors(Search searcher) {
        ArrayList<SearchState> stateList = new ArrayList<SearchState>();

        int iPosition = 0; // used to track the current row of the matrix
        for (int[] i: initialState) {
            for (int k=0;k<i.length;k++) {
                int[][] copyOfInitialState = new int[initialState.length][3]; 
                for (int x=0;x<initialState.length;x++) {
                    System.arraycopy(initialState[x], 0, copyOfInitialState[x], 0, initialState[x].length); // copy of the initial state is made. This is done in order to not change the initial state during potential move finding
                }

                if (k != 0) {  // checks if k cannot move any more to the left
                    if (i[k-1] == 0) {  // if the position to the left of the current one is empty, a left move can be achieved
                        // switches the empty space and the current number
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition][k-1];
                        copyOfInitialState[iPosition][k-1] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState));
                        continue;
                    }
                }

                if (k != 2) {  // checks if k cannot move any more to the right
                    if (i[k+1] == 0) { // if the position to the right of the current one is empty, a right move can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition][k+1];
                        copyOfInitialState[iPosition][k+1] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState)); // appends a new node to the stateList
                        continue;
                    }
                }

                if (iPosition != 0) {  // checks if k cannot move any more to the up
                    if (initialState[iPosition-1][k] == 0) { // if the position above the current one is empty, a move up can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition-1][k];
                        copyOfInitialState[iPosition-1][k] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState));
                        continue;
                    }
                }

                if (iPosition != 2) {  // checks if k cannot move any more to the down
                    if (initialState[iPosition+1][k] == 0) { // if the position below the current one is empty, a move down can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition+1][k];
                        copyOfInitialState[iPosition+1][k] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState));
                        continue;
                    }
                }

            }
            iPosition += 1; // the next row is checked
        }
        return stateList;
    }

    public boolean sameState(SearchState givenSecondState) {
        return false;
    }
    
    public String toString() {
        String output = "\n\n";
        for (int[] i:initialState) {
            output += "|";
            for (int k: i) {
                output += k + " ";
            }
            output += "|\n";
        }
        output += "\n";
        return output;
    }
}
