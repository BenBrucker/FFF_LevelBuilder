package levelFactory;

public enum Direction {
	north, 
	east, 
	south, 
	west;
	
	public static int getXOffset(Direction d)	{
		switch(d)	{
			case north: return 0; 
			case south: return 0;
			case east: return 1;
			case west: return -1;
			default: return 0;
		}
	}
	
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