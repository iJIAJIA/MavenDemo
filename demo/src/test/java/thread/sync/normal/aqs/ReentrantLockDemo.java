package thread.sync.normal.aqs;

import java.util.concurrent.TimeUnit;

public class ReentrantLockDemo {
	
	private static ReentrantLockExt lock = new ReentrantLockExt();
	
	public static void main(String[] args) {
		T1 t1 = new T1();
		
		T2 t21 = new T2();
		T2 t22 = new T2();
		T2 t23 = new T2();
		t1.start();
		t21.start();
		t22.start();
		t23.start();
	}
	
	static class T1 extends Thread{
		@Override
		public void run() {
			try {
				lock.lock();
				System.out.printf("%s locked %n",Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				System.out.printf("%s interrupted %n",Thread.currentThread().getName());
			}finally {
				lock.unlock();
			}
		}
	}
	
	static class T2 extends Thread{
		@Override
		public void run() {
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			try {
				lock.lock();
				System.out.printf("%s t2#locked %n",Thread.currentThread().getName());
			} finally {
				lock.unlock();
			}
		}
	}
	
}
