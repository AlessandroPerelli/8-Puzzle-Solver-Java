public class EpuzzleSearch extends Search {
    private int [][] targetState;

    public EpuzzleSearch (int[][] target) {
	    targetState = target;
    }

    public int[][] getTarget(){
	    return targetState; // used in EpuzzleState when comparing current state to the target state
    }
}
