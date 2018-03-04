package thread.sync.normal.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AQSExt extends AbstractQueuedSynchronizer{
	
	public void acquireExt(int arg) {
		System.out.printf("%s acquire %n",Thread.currentThread().getName());
		acquire(arg);
	}
	
	public void releaseExt(int arg) {
		System.out.printf("%s release %n",Thread.currentThread().getName());
		release(arg);
	}
	
}