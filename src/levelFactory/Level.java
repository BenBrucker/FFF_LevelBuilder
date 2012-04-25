package levelFactory;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import solutionChecker.Graph;
import levelFactory.Direction;

public class Level implements Graph	{
	private int[][] grid;
	private int[][] gridCopy;
	private int rows,columns;
	private ArrayList<Point> goals;
		
	private int heuristicValue;
	private Direction[] directions = {Direction.north,Direction.east,Direction.south,Direction.west};
	
    
	public Level(int[][] grid, int rows, int columns, ArrayList<Point> goals)	{
		this.grid = grid;
		this.gridCopy = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.goals = goals;
	}
	
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
					s.append("\n");
			}
		return s.toString();
	}

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

	@Override
	public Collection<Graph> successors () 
    {
         ArrayList<Graph> successors = new ArrayList<Graph>();
         for (Direction d : directions)	{
        	 successors.add(tilt(d));
         }
         return successors;
    }

	public Level tilt(Direction d) {
		multiArrayCopy(grid, gridCopy);	
		boolean isDone = false;
		int row = 0, column = 0;
		while (!isDone)	{
			isDone = true;
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
		}
		return new Level(gridCopy, this.rows, this.columns, goals);
	}
	
	private void moveBlock(int row, int column, Direction d)	{
		int destRow = row + Direction.getYOffset(d);
		int destCol = row + Direction.getXOffset(d);
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
        			if (lvl.getTile(row,column) != grid[row][column])
        				return false;
        		}
        	}
        	return true;
        }
 	}
    
    /**
     * Gets the tilenumber at x and y.
     * @param x: The x coordinate of the tile.
     * @param y: The y-coordinate of the tile.
     * @return the number of the tile corresponding to the coordinates.
     */
    public int getTile(int x, int y)
    {
    	return grid[x][y];
    }

	@Override
	public int getHeuristic() {
		int heuristic = 0;
		for (Point p : goals)	{
			if (grid[p.x][p.y] != 0)	{
				heuristic += 10; //TODO Better Heuristic
			}
		}
		return heuristic;
	}
}
