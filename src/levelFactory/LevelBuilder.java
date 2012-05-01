package levelFactory;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * A Level Builder will keep producing random levels with specified dimensions.
 * @author Ben Brucker
 */
public class LevelBuilder implements Runnable {
	
	/** The dimensions of this level */
	private int row, column;
	/** The queue this Builder will place constructed levels in */
	private final BlockingQueue<Level> queue;
	/** The random generator used by this Builder */
	public final static Random rand = new Random();
	
	/** The Probabilities for all blocks */
	private final int HORI_PROB = 15, VERT_PROB = 15, ANY_PROB = 10, IMMO_PROB = 45, NOBOX_PROB = 85, GOAL_PROB = 15; 
	
	/** The added values of all probabilities to create categories */
	private final int CAT_1 = HORI_PROB, CAT_2 = CAT_1 + VERT_PROB, CAT_3 = CAT_2 + IMMO_PROB, CAT_4 = CAT_3 + ANY_PROB, CAT_5 = CAT_4 + GOAL_PROB;
	private final int TOTAL = HORI_PROB + VERT_PROB + ANY_PROB + IMMO_PROB + NOBOX_PROB + GOAL_PROB;
	
	/** This levelbuilder will keep producing levels while this is true */
	private boolean keepProducing = true;
	
	/**
	 * Constructor for the levelBuilder
	 * @param row the row-dimension
	 * @param column the column-dimension
	 * @param q the queue this builder can place produced levels in
	 */
	public LevelBuilder(int row, int column, BlockingQueue<Level> q)	{
		this.row = row;
		this.column = column;
		this.queue = q;
	}
	
	@Override 
	public void run()	{
		try {
			while(keepProducing) { 
				queue.put(produce()); 
			}
		} 
		catch (InterruptedException ex)		{
			ex.printStackTrace();
			System.err.println("Producer failed: " + ex.getLocalizedMessage());
		}
	}
	
	/**
	 * Makes a new random level given the probabilities
	 * @return a new level
	 */
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
	
	/** Stops this levelBuilder */
	public void stop()	{
		keepProducing = false;
	}
}
