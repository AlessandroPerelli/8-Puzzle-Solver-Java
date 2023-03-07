
/**
*	Search.java -
*   abstract class specialising to JugsSearch etc
*   includes depth-first and breadth-first strategies
*   reconstructs solution path
*   Phil Green 2013 version
*   Heidi Christensen (heidi.christensen@sheffield.ac.uk) 2021 version
*/

import java.util.*;

public abstract class Search {

	protected SearchNode initNode; // initial node
	protected SearchNode currentNode; // current node

	protected ArrayList<SearchNode> open; // open - list of SearchNodes
	protected ArrayList<SearchNode> closed; // closed - .......
	protected ArrayList<SearchNode> successorNodes; // used in expand & vetSuccessors

	/**
	 * run a search
	 * 
	 * @param initState initial state
	 * @param strat     - String specifying strategy
	 * @return indication of success or failure
	 */
	public String runSearch(SearchState initState, String strategy) {

		initNode = new SearchNode(initState); // create initial node

		System.out.println("Starting " + strategy + " Search"); // change from search1 - print strategy

		open = new ArrayList<SearchNode>(); // initial open, closed
		open.add(initNode);

		closed = new ArrayList<SearchNode>();

		int iterationCount = 1; // counts the iterations

		while (!open.isEmpty()) {

			System.out.println(closed.size());
			// print contents of open
			System.out.println("iteration no " + iterationCount);
			System.out.println("open is");
			for (SearchNode nn : open) {
				String nodestr = nn.toString();
				System.out.println(nodestr);
			}

			selectNode(strategy); // change from search1 - selectNode selects next node, given strategy
			// makes it currentNode & removes it from open
			System.out.println("Current node " + currentNode.toString());

			if (currentNode.goalPredicate(this))
				return reportSuccess(); // success
			// change from search1 - call reportSuccess
			// - must pass search instance to goalP
			expand(); // go again

			closed.add(currentNode); // put current node on closed

			iterationCount = iterationCount + 1;
		}
		;

		return "Search Fails"; // out of the while loop - failure

	}

	// expand current node

	private void expand() {

		// get all successor nodes
		successorNodes = currentNode.getSuccessors(this); // pass search instance

		// filter out unwanted - Dynamic Programming check
		vetSuccessors();

		// add surviving nodes to open
		// change from search1 - set their parents to currentNode
		for (SearchNode snode : successorNodes) {
			open.add(snode);
			snode.setParent(currentNode);
		}
	}

	// vet the successors - reject any whose states are on open or closed

	private void vetSuccessors() {

		ArrayList<SearchNode> vslis = new ArrayList();

		for (SearchNode snode : successorNodes) {
			if (!(onClosed(snode)) && !(onOpen(snode)))
				vslis.add(snode);
		}
		;
		successorNodes = vslis;
	}

	// onClosed - is the state for a node the same as one on closed?

	private boolean onClosed(SearchNode newNode) {
		boolean ans = false;
		for (SearchNode closedNode : closed) {
			if (newNode.sameState(closedNode))
				ans = true;
		}
		return ans;
	}
	// onOpen - is the state for a node the same as one on closed?

	private boolean onOpen(SearchNode newNode) {
		boolean ans = false;
		for (SearchNode openNode : open) {
			if (newNode.sameState(openNode))
				ans = true;
		}
		return ans;
	}

	// select the next node - change from search1
	// call depthFirst or breadthFirst dependent on strat
	// defaults to breadthFirst

	private void selectNode(String strat) {
		if (strat == "depthFirst")
			depthFirst();
		else
			breadthFirst();
	}

	private void depthFirst() {
		int osize = open.size();
		currentNode = (SearchNode) open.get(osize - 1); // last node added to open
		open.remove(osize - 1); // remove it
	}

	private void breadthFirst() {
		currentNode = (SearchNode) open.get(0); // first node on open
		open.remove(0);
	}

	// change from search1
	// report success - reconstruct path, convert to string & return
	private String reportSuccess() {

		SearchNode n = currentNode;
		StringBuffer buf = new StringBuffer(n.toString());
		int plen = 1;

		while (n.getParent() != null) {
			buf.insert(0, "\n");
			n = n.getParent();
			buf.insert(0, n.toString());
			plen = plen + 1;
		}

		System.out.println("=========================== \n");
		System.out.println("Search Succeeds");

		System.out.println("Efficiency " + ((float) plen / (closed.size() + 1)));
		System.out.println("Solution Path\n");
		return buf.toString();
	}
}
