package levelFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import solutionChecker.Solver;

/**
 * A Level Checker will keep Checking if levels have solutions and save them in text files.
 * @author Ben Brucker
 */
public class LevelChecker implements Runnable {
	/** The queue where this checker gets its levels */
	private final BlockingQueue<Level> queue;
	/** The solver that will be used to see if a level is solvable */
	private Solver solver;
	/** The producer of the levels */
	private LevelBuilder producer;
	/** This array holds all files this checker will save levels in */
	private BufferedWriter[] out;
	/** The maximum amount of steps to be checked */
	private final int MAX_STEPS = 100;
	/** The maximum number of solutions that will be found */
	private final int MAX_SOLUTIONS = 200;
	/** Counter for the number of found solutions */
	private int currentSolutions = 0;
	/** This will keep checking levels while this is true */
	private boolean keepConsuming = true;
	
	/**
	 * Constructor for this level checker
	 * @param q the queue that this will get levels from
	 * @param p the producer that produces levels for this checker
	 */
	public LevelChecker(BlockingQueue<Level> q, LevelBuilder p) { 
		this.queue = q; 
		this.producer = p;
		this.out = new BufferedWriter[MAX_STEPS];
	}
	   
	@Override 
	public void run()	{
		try	{
			while (keepConsuming) {
				consume(queue.take());
				if (currentSolutions >= MAX_SOLUTIONS)	{
					producer.stop();
					keepConsuming = false;
					queue.clear();
				}
			} 
		}
		catch (InterruptedException | IOException ex)	{
			ex.printStackTrace();
			System.err.println("Consumer failed: " + ex.getLocalizedMessage());
		}
		for (int i = 0; i < MAX_STEPS; i++)
		if (out[i] != null)
			try {
				out[i].close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This will check if a level is solvable, if it is, then it will be saved in a file.
	 * @param level the level to be checked.
	 * @throws IOException
	 */
	private void consume(Level level) throws IOException	{
		/* TESTLEVEL
		int[][] testing = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				testing[i][j] = 0;
		testing[2][0] = 3;
		testing[1][1] = 3;
		testing[1][0] = 4;
		ArrayList<Point> testList = new ArrayList<Point>();
		testList.add(new Point(2,1));
		Level test = new Level(testing, 3, 3, testList);
		*/
		solver = new Solver(level);
		int steps = solver.solve();
		if (steps > 0)	{
			if (out[steps] == null)	{
				out[steps] = new BufferedWriter(new FileWriter(".\\l" + level.getRows() + "x"+level.getColumns() + "steps" + steps + ".txt"));
			}
			out[steps].write(level.toString());
			System.out.println(level);
			currentSolutions++;
		}
	}
	
	/**
	 * Checks whether this is done
	 * @return true if not consuming anymore
	 */
	public boolean isDone()	{
		return !this.keepConsuming;
	}

}