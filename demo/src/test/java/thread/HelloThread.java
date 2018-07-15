package thread;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HelloThread {
	
	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "invoke...");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("blabla");
			}
		};
		for(int i = 0; i < 10; i++) {
			Thread thread = new Thread(runnable);
			thread.setName("thread_" + i );
			thread.start();
			try {
				TimeUnit.SECONDS.timedJoin(thread, 2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i + "join end");
		}
		
	}
}
