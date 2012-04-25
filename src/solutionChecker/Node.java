package solutionChecker;

/**
 * This class models a node that references information about an item and about its parent.
 * @author Ben Brücker, 0413291, Informatica
 * @author Rick Erkens, 4100573, Informatica
 * @author Pieter Koopman, Sjaak Smetsers, Provided the class skeleton
 * @date Mar 17, 2012
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>>
{
	/** The item this class is referencing */
    private T item;
    
    /** This node's parent */
    private Node<T> previous;

    /**
     * Constructor for the Node class
     * @param from is the parent Node
     * @param to the reference to the item this is holding
     */
    public Node (Node<T> from, T to) 
    {
        previous    = from;
        item        = to;
    }

    /**
     * Gets the item this is referencing
     * @return the item this references
     */
    public T getItem() 
    {
        return item;
    }

    /**
     * Gets the parent Node
     * @return the parent node
     */
    public Node<T> getPrevious()
    {
        return previous;
    }

    /**
     * Gets the length of the chain of nodes from this Node to its oldest parent.
     * @return the length of the node path
     */
    public int length () 
    {
    	if (previous != null)
    		return 1+previous.length();
    	else
    		return 0;
    }
    
    @Override
    public String toString() 
    {/*
    	if (previous != null)
    	{
    		return previous.toString() + "\n" + item;
    	}
    	else 
    		return "Start Configuration:\n" + item.toString();*/
    	return " ";
    }

	@Override
	public int compareTo(Node<T> o) 
	{
		return this.item.compareTo(o.getItem());
	}
}