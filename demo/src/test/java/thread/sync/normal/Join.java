package thread.sync.normal;

import java.util.concurrent.TimeUnit;

public class Join {
	
	public static void main(String[] args) {
		Father father = new Father();
		father.start();
	}
	
	static class Father extends Thread{
		@Override
		public void run() {
			System.out.println("father running");
			Son son = new Son();
			son.start();
			try {
//				join源码:调用son#isAlive()方法检测son是否仍存活,如果son存活,则使当前线程进入等待阻塞wait()方法
//				wait()的作用是让“当前线程”等待，而这里的“当前线程”是指当前在CPU上运行的线程。所以，虽然是调用子线程的wait()方法，但是它是通过“主线程”去调用的；所以，休眠的是主线程，而不是“子线程”！
				son.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("father dead");
		}
	}
	
	static class Son extends Thread{
		@Override
		public void run() {
			System.out.println("son running");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("son dead");
		}
	}
}
