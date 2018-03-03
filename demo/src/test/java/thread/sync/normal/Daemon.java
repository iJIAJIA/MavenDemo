package thread.sync.normal;

public class Daemon {
	
	public static void main(String[] args) {
		DaemonT dt = new DaemonT();
		dt.setDaemon(true);
		dt.start();
	}
	
	/**
	 * 所谓守护线程是指在程序运行的时候在后台提供一种通用服务的线程，
	 * 比如垃圾回收线程就是一个很称职的守护者，并且这种线程并不属于程序中不可或缺的部分。
	 * 因 此，当所有的非守护线程结束时，程序也就终止了，同时会杀死进程中的所有守护线程。
	 * 反过来说，只要任何非守护线程还在运行，程序就不会终止。
	 * @author Administrator
	 *
	 */
	static class DaemonT extends Thread{
		@Override
		public void run() {
			while(true) {
				System.out.println("daemon thread alive");
			}
		}
	}
	
	static class T extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
		
	}
}
