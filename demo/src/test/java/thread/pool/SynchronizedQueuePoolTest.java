package thread.pool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SynchronizedQueuePoolTest {
	
	public static void main(String[] args) {
		
		
		
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 5,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>(),new RejectedHandler());
		for(int i = 0; i < 10; i++ ){
			pool.execute(new DefaulRunnable(i));
		}
		
	}
	
}
