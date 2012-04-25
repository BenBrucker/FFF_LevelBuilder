package levelFactory;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;


public class LevelBuilder implements Runnable {
	
	private int row, column;
	private final BlockingQueue<Level> queue;
	public final static Random rand = new Random();
	private final int HORI_PROB = 10, VERT_PROB = 10, ANY_PROB = 15, IMMO_PROB = 20, NOBOX_PROB = 65, GOAL_PROB = 15; 
	private final int CAT_1 = HORI_PROB, CAT_2 = CAT_1 + VERT_PROB, CAT_3 = CAT_2 + IMMO_PROB, CAT_4 = CAT_3 + ANY_PROB, CAT_5 = CAT_4 + GOAL_PROB;
	private final int TOTAL = HORI_PROB + VERT_PROB + ANY_PROB + IMMO_PROB + NOBOX_PROB + GOAL_PROB; 
	
	public LevelBuilder(int row, int column, BlockingQueue<Level> q)	{
		this.row = row;
		this.column = column;
		this.queue = q;
	}
	
	@Override 
	public void run()	{
		try {
			while(true) { 
				queue.put(produce()); 
			}
		} 
		catch (InterruptedException ex)		{
			ex.printStackTrace();
			System.err.println("Producer failed: " + ex.getLocalizedMessage());
		}
	}
	
	private Level produce() {
		int[][]grid = new int[row][column];
		ArrayList<Point> goals = new ArrayList<Point>();
		int newRand = 0;
		for (int x = 0; x < row; x++)	{
			for (int y = 0; y < column; y++)	{
				newRand = rand.nextInt(TOTAL);
				if (newRand < CAT_1)	{
					grid[x][y] = 1;
				} else if (newRand < CAT_2)	{
					grid[x][y] = 2;
				} else if (newRand < CAT_3)	{
					grid[x][y] = 3;
				} else if (newRand < CAT_4)	{
					grid[x][y] = 4;
				} else if (newRand < CAT_5)	{
					goals.add(new Point(x,y));
				} else if (newRand < TOTAL)	{
					grid[x][y] = 0;
				}
			}
		}
		return new Level(grid, row, column, goals);
	}
}
