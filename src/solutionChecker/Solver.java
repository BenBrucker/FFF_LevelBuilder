package solutionChecker;

import java.util.*;

/**
 * This class will solve a graph
 * @author Ben Brücker, 0413291, Informatica
 * @author Rick Erkens, 4100573, Informatica
 * @author Pieter Koopman, Sjaak Smetsers, Provided the class skeleton
 * @date Mar 17, 2012
 */
public class Solver
{
	/** The queue of nodes that will be considered solutions */
	@SuppressWarnings("rawtypes")
	Queue<Node> toExamine;
	
	/** A hash set that will track solutions to prevent duplicates */
	HashSet<Integer> hash;
	
	/**
	 * Constructor for the Solver class.
	 * @param graph is the initial configuration to be considered
	 */
	@SuppressWarnings("rawtypes")
	public Solver(Graph graph) 
    {
    	toExamine = new LinkedList<Node>();
    	Node<Graph> toAdd = new Node<Graph>(null, graph);
    	toExamine.add(toAdd);
    	hash = new HashSet<Integer>();
    	hash.add(toAdd.hashCode());
    }
	
	
	/**
	 *  Will solve the current graph. 
	 *  @return Returns success and failure messages.
	 */
	public int solve () 
    {
        while (! toExamine.isEmpty() ) 
        {
            @SuppressWarnings("unchecked")
			Node<Graph> next = toExamine.remove();
            Graph current = next.getItem();
            if (current.isGoal())
            {
            	//System.out.println(next);
                return next.length();
            } 
            else 
            {
                for (Graph succ: current.successors()) 
                {
                	if (hash.add(succ.hashCode()))	{
                		toExamine.add(new Node<Graph>(next, succ));
                	}
                }
            }
        }
        return 0;
    }
}