package levelFactory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import solutionChecker.Graph;
import levelFactory.Direction;

/**
 * A Level holds all information about the position of all Blocks.
 * It implements Graph so it will be solvable by an Artifical Intelligence function working with Graphs
 * @author Ben Brucker
 */
public class Level implements Graph	{
	/** The grid of blocks as integer representation */
	private int[][] grid;
	/** A private temporary copy of grid, so grid won't be modified */
	private int[][] gridCopy;
	/** The amount of rows and columns in this level */
	private int rows,columns;
	/** An array that holds the coordinates of the goal squares. */
	private ArrayList<Point> goals;
	/** The Heuristic value of this Graph TODO Not yet used */
	private int heuristicValue = 0;
	/** An array of all 4 possible directions, for the ease of looping through them */
	private Direction[] directions = {Direction.north,Direction.east,Direction.south,Direction.west};
	
    /**
     * Constructor for a level
     * @param grid the position and type of the blocks that this level contains
     * @param rows the amount of rows of this level
     * @param columns the amount of columns of this level
     * @param goals An array list holding the coordinates of the goal squares
     */
	public Level(int[][] grid, int rows, int columns, ArrayList<Point> goals)	{
		this.grid = new int[rows][columns];
		multiArrayCopy(grid, this.grid);
		this.gridCopy = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.goals = goals;
	}
	
	/**
	 * Gets the integer representation of this level
	 * @return the grid that holds the integer representation of this level
	 */
	public int[][] getLevel()	{
		return grid;
	}
	
	@Override
	public String toString()	{
		StringBuilder s = new StringBuilder("");
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)	{
				s.append(grid[i][j]);
				if (j == (columns - 1))
					s.append("\\n");
			}
		for (Point goal : goals)
			s.setCharAt(goal.x * (columns + 2) + goal.y, '5');
		s.append("\n");
		return s.toString();
	}
	
	/** Compares this graph to another graph. TODO: Not yet working correctly since heuristics are missing */
    @Override
    public int compareTo(Graph g) throws NullPointerException
    {
    	if (g == null)
    		throw new NullPointerException();
        if (heuristicValue < g.getHeuristic())
        	return -1;
        else if (heuristicValue == g.getHeuristic())
        	return 0;
        else 
        	return 1;
    }

    /** Gets all successors of this graph by trying to tilt it in all 4 directions */
	@Override
	public Collection<Graph> successors () 
    {
         ArrayList<Graph> successors = new ArrayList<Graph>();
         for (Direction d : directions)	{
        	 successors.add(tilt(d));
         }
         return successors;
    }
	
	/**
	 * Gets the result of tilting this level in a certain direction
	 * @param d the direction this level gets tilted in.
	 * @return a new Level containing a tilted version of this level.
	 */
	public Level tilt(Direction d) {
		multiArrayCopy(grid, gridCopy);
		int row = 0, column = 0;
			for (int i = 0; i < this.rows; i++)	{
				for (int j = 0; j < this.columns; j++)	{
					switch (d)	{
						case north: 
						case west: row = i; column = j; break;
						case east:
						case south: row = this.rows - (i + 1); column = this.columns - (j + 1); break;
					}
					if (gridCopy[row][column] != 0)
						moveBlock(row, column, d);
			}	
		}
		return new Level(gridCopy, this.rows, this.columns, goals);
	}
	
	/**
	 * This function moves a single block as far as possible in the indicated direction
	 * @param row the inital row of this block
	 * @param column the initial column of this block
	 * @param d the direction this block should move in.
	 */
	private void moveBlock(int row, int column, Direction d)	{
		int destRow = row + Direction.getYOffset(d);
		int destCol = column + Direction.getXOffset(d);
		if (BlockMover.moveBlock(gridCopy[row][column], d))	{
			boolean canMove = true;
			while (canMove)	{
				if (destRow < 0 || destRow >= this.rows ||
					destCol < 0 || destCol >= this.columns ||
					row < 0 || row >= this.rows ||
					column < 0 || column >= this.columns ||
					gridCopy[destRow][destCol] != 0)
						canMove = false;
				if (canMove)	{
					gridCopy[destRow][destCol] = gridCopy[row][column];
					gridCopy[row][column] = 0;
					destRow += Direction.getYOffset(d);
					destCol += Direction.getXOffset(d);
					row += Direction.getYOffset(d);
					column += Direction.getXOffset(d);
				}
			}
		}
	}
	
	/** Checks whether this level is done */
	@Override
	public boolean isGoal() {
		boolean isDone = true;
		if (goals.size() == 0)
			return false;
		for (Point p : goals)	{
			if (grid[p.x][p.y] == 0)	{
				isDone = false;
				break;
			}
		}
		return isDone;
	}
	
	/**
	 * Copies an integer array
	 * @param source the source array
	 * @param destination the target array
	 */
	public void multiArrayCopy(int[][] source,int[][] destination)
	{
		for (int a=0;a<source.length;a++)	{
			System.arraycopy(source[a],0,destination[a],0,source[a].length);
		}
	}

    @Override
    public int hashCode()
    {
    	int hashCode = 0;
    	for (int row = 0; row < this.rows; row++)
    	{
    		for (int col = 0; col < this.columns; col++)
    		{
    			hashCode += grid[row][col] * (row * this.rows + col * this.columns) + (row * this.rows + col * this.columns);
    		}
    	}
    	return hashCode;
    }   
    
    @Override
    public boolean equals(Object o) {
        if (o == null)
        	return false;
        else if (getClass() != o)
        	return false;
        else 
        {
        	Level lvl = (Level) o;
        	for (int row = 0; row < this.rows; row++)
        	{
        		for (int column = 0; column < this.columns; column++)
        		{
        			if (lvl.getBlock(row,column) != grid[row][column])
        				return false;
        		}
        	}
        	return true;
        }
 	}
    
    /**
     * Gets the block at x and y.
     * @param x: The row coordinate of the tile.
     * @param y: The column-coordinate of the tile.
     * @return the number of the block corresponding to the coordinates.
     */
    public int getBlock(int x, int y)
    {
    	return grid[x][y];
    }

	@Override
	public int getHeuristic() {
		return 0; //TODO Find a good Heuristic
	}
	
	/** Getter for the rows */
	public int getRows()	{
		return rows;
	}
	
	/** Getter for the columns */
	public int getColumns()	{
		return columns;
	}
}
