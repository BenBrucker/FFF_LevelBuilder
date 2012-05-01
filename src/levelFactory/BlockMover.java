package levelFactory;

/**
 * Determines if a block indicated by the given number can move in the specified direction
 * @author Ben Brucker
 */
public class BlockMover {
	
	/**
	 * Returns whether a block can move in the given direction
	 * @param blocktype: 1 = Horizontal, 2 = Vertical, 3 = Immobile, 4 = Any Direction
	 * @param d The direction the block should try to move
	 * @return true if a block can move in the given direction
	 */
	public static boolean moveBlock(int blocktype, Direction d)	{
		switch(blocktype)	{
		case 1: if (d == Direction.west || d == Direction.east) return true; else return false;
		case 2: if (d == Direction.north || d == Direction.south) return true; else return false;
		case 3: return false;
		case 4: return true;
		default: return false;
		}
	}

}
