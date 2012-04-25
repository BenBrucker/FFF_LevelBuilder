package levelFactory;
import java.util.concurrent.BlockingQueue;

import solutionChecker.Solver;



public class LevelChecker implements Runnable {
	
	private final BlockingQueue<Level> queue;
	private Solver solver;
	private Runnable producer;

	public LevelChecker(BlockingQueue<Level> q, Runnable r) { 
		this.queue = q; 
		this.producer = r;
	}
	   
	@Override 
	public void run()	{
		try {
			while(true) { 
				consume(queue.take());
			}
		} 
		catch (InterruptedException ex)	{
			ex.printStackTrace();
			System.err.println("Consumer failed: " + ex.getLocalizedMessage());
		}
	}
	
	private void consume(Level level)	{
		solver = new Solver(level);
		int steps = solver.solve();
		if (steps > 0)
			System.out.println(level);
	}
}