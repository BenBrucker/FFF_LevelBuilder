package solutionChecker;

import java.util.Collection;

/**
 * This interface guarantees the correct coordination between a solver, and a class that implements this Graph
 * @author Ben Brücker, 0413291, Informatica
 * @author Rick Erkens, 4100573, Informatica
 * @author Pieter Koopman, Sjaak Smetsers, The original authors of this interface
 */
public interface Graph extends Comparable<Graph> 
{
	/**
	 * Gets all successors of a node in the graph
	 * @return a Collection of successor nodes
	 */
	public Collection<Graph> successors ();
	
	/**
	 * Checks whether this node in the Graph is the goal
	 * @return true if the goal is reached, false otherwise
	 */
	public boolean isGoal ();
	
	/**
	 * Gets the heuristic value of the graph, for A* search.
	 * @return the heuristic value of the graph.
	 */
	public int getHeuristic();
}