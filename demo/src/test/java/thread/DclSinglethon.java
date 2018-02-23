package thread;
/**
 * 双检测锁定单例 (DCL:double check lock)
 * @author Administrator
 *
 */
public class DclSinglethon {
	/*
	
	private static DclSinglethon target;
	
	public DclSinglethon getInstance() {
		这种写法是非线程安全的,线程1拿到target时,target还未初始化,进入到A点,此时线程1挂起
		线程2拿到target,也进入A点,会导致DclSinglethon 实例化了两次,不符合单例原则
		if(target == null) {
//			A
			this.target = new DclSinglethon();
		}
		return target;
	}
	
	*/
	
	/*
	 
	private static DclSinglethon target;
	
	在 getInstance上加锁,可以保证对象只实例化一次,但后续调用getInstance,都会进行锁操作,耗性能
	public synchronized DclSinglethon getInstance() {
		if(target == null) {
			this.target = new DclSinglethon();
		}
		return target;
	}
	*/
	
	/*
	private static DclSinglethon target;
	
	public static DclSinglethon getInstance() {
		这种写法跟在方法上加锁一样性能不友好
		synchronized (DclSinglethon.class) {
			if(target == null) {
				target = new DclSinglethon();
			}
		}
		
		return target;
	}
	*/
//	TODO 
	private static DclSinglethon target;
	
	public static DclSinglethon getInstance() {
		if(target == null) {
			synchronized (DclSinglethon.class) {
//				TODO synchronized为什么不能保证 target = new DclSinglethon()的原子性?
				if(target == null) {
					target = new DclSinglethon();
				}
			}
		}
		return target;
	}
}
