package thread.sync.normal;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ParkDemo {
	
	public static void main(String[] args) {
			TWithLockSupport tWithLockSupport = new TWithLockSupport();
			TWithSync tWithSync = new TWithSync();
			TWithReentranLock tWithReentranLock = new TWithReentranLock();
			tWithLockSupport.start();
			tWithSync.start();
			tWithReentranLock.start();
			// 在t1阻塞于第二次park的时候,我们在外部调用t1.interrupt()
			// 此时t1 LockSupport#park()能够响应这次中断,但不会抛出异常
			// 而是从原来的阻塞点接着执行线程内的代码,直到线程结束
			// interrupting a thread unparks it.
			tWithLockSupport.interrupt();
	}
	
	static class TWithLockSupport extends Thread{
		@Override
		public void run() {
			LockSupport.unpark(this);
			// 许可默认被占用
			// LockSupport.unpark(Thread.currentThread()); 不执行这一步操作的话,LockSupport.park()不会获得许可
			LockSupport.park(this);
			System.out.println("first park");
			// 对于同一个线程来讲,LockSupport#park()获得的许可证是不可重入
			// 区别于synchronize
			LockSupport.park(this);
			System.out.println("second park");
		}
	}
	
	static class TWithReentranLock extends Thread{
		
		private Lock lock = new ReentrantLock();
		
		@Override
		public void run() {
			lock.lock();
			System.out.println("first reentrant lock");
			lock.lock();
			System.out.println("second reentrant lock");
		}
	}
	
	static class TWithSync extends Thread {
		@Override
		public void run() {
			synchronized (this) {
				System.out.println("first sync lock");
				// 在同一条线程,sync同步块是可重入的
				synchronized (this) {
					System.out.println("second sync lock");
				}
			}
		}
	}
}
