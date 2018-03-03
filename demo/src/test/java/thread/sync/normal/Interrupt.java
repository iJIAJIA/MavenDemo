package thread.sync.normal;

import java.util.concurrent.TimeUnit;

public class Interrupt {
	
	public static void main(String[] args) {
//		自己终止
//		T t = new T();
//		t.start();
//		外部终止
		T2 t2 = new T2();
		t2.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		t2.close();
		t2.interrupt();
		
	}
	
	static class T2 extends Thread{
		
		volatile boolean endFlag = false;
		
		
		@Override
		public void run() {
			System.out.println("run invoke");
//			while(!endFlag) {
//				try {
//					sleep(0);
//				} catch (InterruptedException e) {
//					System.out.println("interrupted");
//					break;
//				}
//			}
			while(!isInterrupted()) {
				try {
					System.out.println("wait blocked");
					sleep(0);
				} catch (InterruptedException e) {
//					当前线程处于等待阻塞状态时,调用Thread#interrupt()方法,
//					会中断线程的阻塞状态,抛出InterruptedException,并将阻塞状态改为false
					System.out.println("interrupted");
				}
			}
			System.out.println("dead");
		}
		
		public void close() {
			this.endFlag = true;
		}
	}
	
	static class T extends Thread{
		@Override
		public void run() {
			int i = 0;
			while(!isInterrupted()) {
				i++;
				System.out.println(i);
				if(i == 2) {
//					将当前线程设置为终止状态
					interrupt();
				}
			}
		}
	}
}
