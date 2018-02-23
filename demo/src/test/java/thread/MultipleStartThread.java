package thread;

import org.junit.Test;

public class MultipleStartThread {
	
	@Test
	public void test(){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " has run");
			}
		});
		thread.start();
		while(true){
			
		}
	}
}
