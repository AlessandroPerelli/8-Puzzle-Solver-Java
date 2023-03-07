import java.util.ArrayList;
import java.util.Arrays;

public class EpuzzleState extends SearchState {

    private int [][] initialState;
    private int localCost;
    private int estRemCost;
    private int ercCalculationMethod;

    public EpuzzleState(int[][] givenState, int lc, int erc) {
        initialState = givenState;
        localCost = lc;
        estRemCost = erc;
        ercCalculationMethod = 1; // 0 means that hamming method is used, whilst any other number means that manhattan method is used instead
    }

    public int getLocalCost() {
        return localCost;
    }

    public int getestRemCost() {
        int[][] target = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        estRemCost = 0;
        // this goes through the current state matrix and compares the position of the items in it to the relative position of the target state matrix
        if (ercCalculationMethod == 0) {
            for (int i=0;i<initialState.length;i++) {
                for (int k=0;k<initialState[i].length;k++) {
                    if (initialState[i][k] != target[i][k] && initialState[i][k] != 0) {
                        estRemCost += 1;
                    }
                }
            }
        }
        // this is for the manhattan method, the implementation works by checking each item and seeing how far away it is from the correctRow and correctColumn in which it is supposed to be in
        else {
            int correctRow = 0;
            int correctColumn = 0;
            for (int i=1;i<=9;i++) {
                for (int k=0;k<initialState.length;k++) {
                    for (int j=0;j<initialState[k].length;j++) {
                        if (initialState[k][j] == i) {
                            estRemCost += Math.abs(k-correctRow) + Math.abs(j-correctColumn);
                        }
                    }
                }
                if (correctColumn == 2) {
                    correctRow += 1;
                    correctColumn = 0;
                }
                else {
                    correctColumn += 1;
                }
            }
        }
        return estRemCost;
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
                    System.arraycopy(initialState[x], 0, copyOfInitialState[x], 0, initialState[x].length); // copy of the initial state is made. This is done in order to not change the initiak state during potential move finding
                }

                if (k != 0) {  // checks if k cannot move any more to the left
                    if (i[k-1] == 0) {  // if the position to the left of the current one is empty, a left move can be achieved
                        // switches the empty space and the current number
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition][k-1];
                        copyOfInitialState[iPosition][k-1] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState, getLocalCost(), getestRemCost()));                        
                        continue;
                    }
                }

                if (k != 2) {  // checks if k cannot move any more to the right
                    if (i[k+1] == 0) { // if the position to the right of the current one is empty, a right move can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition][k+1];
                        copyOfInitialState[iPosition][k+1] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState, getLocalCost(), getestRemCost())); // appends a new node to the stateList
                        continue;
                    }
                }

                if (iPosition != 0) {  // checks if k cannot move any more to the up
                    if (initialState[iPosition-1][k] == 0) { // if the position above the current one is empty, a move up can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition-1][k];
                        copyOfInitialState[iPosition-1][k] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState, getLocalCost(), getestRemCost()));                        
                        continue;
                    }
                }

                if (iPosition != 2) {  // checks if k cannot move any more to the down
                    if (initialState[iPosition+1][k] == 0) { // if the position below the current one is empty, a move down can be achieved
                        copyOfInitialState[iPosition][k] = copyOfInitialState[iPosition+1][k];
                        copyOfInitialState[iPosition+1][k] = initialState[iPosition][k];
                        stateList.add(new EpuzzleState(copyOfInitialState, getLocalCost(), getestRemCost()));                        
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
