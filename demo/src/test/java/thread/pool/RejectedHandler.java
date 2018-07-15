package thread.pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedHandler implements RejectedExecutionHandler{
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.printf("rejected runnable:%s \n",r.getClass().getName());
		System.out.printf("poolSize:%d\n",executor.getPoolSize());
	}
}

