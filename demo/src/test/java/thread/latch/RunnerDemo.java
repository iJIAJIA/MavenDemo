package thread.latch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class RunnerDemo {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss'SSS");
	
	private static volatile boolean startOrder = false;
	
	public static void main(String[] args) {

	}
	
	static class Referee {
		
	}
	
	static class Dasher implements Callable<Long>{
		
		public void prepare(){
			System.out.printf("dasher[%s] get ready %n",Thread.currentThread().getName());
		}
		
		public void dash(){
			SimpleDateFormat sdfCopy = (SimpleDateFormat) sdf.clone();
			String startTimeStr = sdfCopy.format(new Date());
			System.out.printf("%s dasher start dashing in %s %n",Thread.currentThread().getName(),startTimeStr);
		}
		
		@Override
		public Long call() throws Exception {
			prepare();
			while(!startOrder){
				
			}
			long startTime = System.currentTimeMillis();
			dash();
			return System.currentTimeMillis() - startTime;
		}
	}
}
