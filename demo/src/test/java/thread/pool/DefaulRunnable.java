package thread.pool;

import java.util.concurrent.TimeUnit;

public class DefaulRunnable implements Runnable{
	String name;
	
	public DefaulRunnable(int i){
		this.name = i + "";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.printf("name:%s invoke...\n",this.name);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("name:%s end\n",this.name);
	}
}
