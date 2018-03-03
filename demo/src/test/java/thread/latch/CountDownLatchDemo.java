package thread.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	
	static CountDownLatch countDownLatch;
	
	public static void main(String[] args) {
		
		int maxThread = 10;
		
		countDownLatch = new CountDownLatch(maxThread);
		
		for(int i = 0; i<10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.printf("%s invoke... %n",Thread.currentThread().getName());
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.printf("%s dead %n",Thread.currentThread().getName());
					countDownLatch.countDown();
				}
			}).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("main dead");
	}
	
}
