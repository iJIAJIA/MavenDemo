package thread.threadlocal;

public class ThreadLocalTest {
	
	private static ThreadLocal<Integer> intholder = new ThreadLocal<Integer>();
	
	
	public static void main(String[] args) {
		
		for(int i = 0 ; i < 10; i++){
			new Task().run();
		}
	
	}
	
	static class Task extends Thread{
		@Override
		public void run() {
			int max = 10000;
			for(int i = 0; i < max; i++){
				intholder.set(i);
			}
			Integer integer = intholder.get();
			System.out.printf("%s thread safe: %b,get=%d\n",Thread.currentThread().getName(),(max == intholder.get()),integer);
		}
	}
}
