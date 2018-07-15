package thread.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleCountDownLatchDemo {
	
	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch startLatch = new CountDownLatch(1);
		
		for(int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.printf("%s invoked %n",Thread.currentThread().getName());
					try {
						startLatch.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.printf("%s ended %n",Thread.currentThread().getName());
				}
			}).start();
		}
		
		TimeUnit.SECONDS.sleep(1);
		startLatch.countDown();
	}
}
