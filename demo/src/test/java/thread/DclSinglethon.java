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
	/*
	private  static DclSinglethon target;
	
	public static DclSinglethon getInstance() {
		if(target == null) {
			synchronized (DclSinglethon.class) {
//				TODO synchronized为什么不能保证 target = new DclSinglethon()的原子性?
//				由于target = new DclSinglethon() 分为如下3个步骤
//				1.new thread.DclSinglethon [1] 给DclSinglethon分配内存 
//			    dup
//			    2.invokespecial thread.DclSinglethon() [19] 初始化DclSinglethon 
//			    3.putstatic thread.DclSinglethon.target : thread.DclSinglethon [17] 将target指向DclSinglethon的实例内存地址
//			    aload_0
//				这里执行的顺序可以为 1-2-3或者1-3-2
//				当顺序为1-3-2时,线程1在执行到3步骤时,线程2刚好调用getInstance,因为target此时已经不为null,所以直接返回实例对象
//				之后由于实例对象还未初始化,线程2对实例的调用会报错
//				解决方法,给target加上volatile修饰符,保证target = new DclSinglethon()执行顺序的有序性
				if(target == null) {
					target = new DclSinglethon();
				}
			}
		}
		return target;
	}
	*/
//	第二把锁
	private volatile static DclSinglethon target;
	
	public static DclSinglethon getInstance() {
		if(target == null) {
//			第一把锁
			synchronized (DclSinglethon.class) {
				if(target == null) {
					target = new DclSinglethon();
				}
			}
		}
		return target;
	}
	public static void main(String[] args) {
		DclSinglethon.getInstance();
	}
}
