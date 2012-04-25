package levelFactory;
import java.util.concurrent.BlockingQueue;



public class LevelChecker implements Runnable {
	
	private final BlockingQueue<Level> queue;

	public LevelChecker(BlockingQueue<Level> q) { 
		this.queue = q; 
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
		System.out.println(level);
	}
}