package thread.sync.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Callable<String> callable = new Callable<String>() {

			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				return "callable result";
			}
		};
		
		FutureTask<String> futureTask = new FutureTask<String>(callable);
		
		new Thread(futureTask).start();
		
//		while(!futureTask.isDone()) {
//			System.out.println("wait");
//		}
		String result = futureTask.get();
		System.out.printf("future task result: %s" ,result);
		
	}
}
