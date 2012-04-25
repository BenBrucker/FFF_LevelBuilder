package levelFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Main {
	public static void main(String[] args) {
		BlockingQueue<Level> q = new LinkedBlockingQueue<Level>(15);
		LevelBuilder build = new LevelBuilder(2,2,q);
		LevelChecker check = new LevelChecker(q);
		new Thread(build).start();
		new Thread(check).start();
	}
}