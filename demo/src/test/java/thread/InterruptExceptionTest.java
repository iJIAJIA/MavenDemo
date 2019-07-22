package thread;

import java.util.concurrent.TimeUnit;

public class InterruptExceptionTest {
	
	public static void main(String[] args) {
		JobThread jobThread = new JobThread();
		jobThread.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("job thread interrupt at " + System.currentTimeMillis());
		jobThread.interrupt();
	}
	
	static class JobThread extends Thread{
		
		@Override
		public void run() {
			new Job().doLongTimeThing();
			System.out.println("job thread run at " + System.currentTimeMillis());
		}
	}
	
	static class Job{
		public void doLongTimeThing() {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("job catch InterruptedException at " + System.currentTimeMillis() );
			}
		}
	}
}
