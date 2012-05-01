package levelFactory;

/**
 * A enum for the 4 directions a block can move. 
 * Also provides offset functions for all directions along the x and y axis.
 * @author Ben Brucker
 */
public enum Direction {
	north, 
	east, 
	south, 
	west;
	
	/**
	 * Gets the xOffset(Column, 0 is left)
	 * @param d the direction
	 * @return -1, 0 or 1, depending on if move in the given direction is possible along this axis
	 */
	public static int getXOffset(Direction d)	{
		switch(d)	{
			case north: return 0; 
			case south: return 0;
			case east: return 1;
			case west: return -1;
			default: return 0;
		}
	}
	
	/**
	 * Gets the yOffset(Row, 0 is top row)
	 * @param d the direction
	 * @return -1, 0 or 1, depending on if move in the given direction is possible along this axis
	 */
	public static int getYOffset(Direction d)	{
		switch(d)	{
			case north: return -1; 
			case south: return 1;
			case east: return 0;
			case west: return 0;
			default: return 0;
		}
	}
}