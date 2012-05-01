package levelFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {
	/** The dimensions of a level */
	private final static int ROW_DIMENSION = 5;
	private final static int COLUMN_DIMENSION = 5;
	
	/**
	 * Main function
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		BlockingQueue<Level> q = new LinkedBlockingQueue<Level>(15);
		LevelBuilder build = new LevelBuilder(ROW_DIMENSION,COLUMN_DIMENSION,q);
		LevelChecker check = new LevelChecker(q, build);
		new Thread(build).start();
		new Thread(check).start();
	}
}