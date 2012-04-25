package levelFactory;

public class BlockMover {
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
