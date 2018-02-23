package thread;

/**
 * 懒汉式单例加载
 * @author Administrator
 *
 */
public class LazySinglethon {
	
	
	public static LazySinglethon getInstance() {
//		SinglethonHolder为LazySinglethon的私有静态类.
//		SinglethonHolder.INSTANCE 字段为静态字段,此时会触发SinglethonHolder的初始化操作
//		类初始化时,会初始化静态字段的值.
//		jvm保证类只有进行一次初始化
		return SinglethonHolder.INSTANCE;
	}
	
	static {
		System.out.println("LazySinglethon cinit");
	}
	
	private LazySinglethon() {
		System.out.println("LazySinglethon init");
	}
	
	private static class SinglethonHolder{
		static LazySinglethon INSTANCE = new LazySinglethon();
		
		static {
			System.out.println("SinglethonHolder cinit");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("blabla");
		LazySinglethon.getInstance();
	}
}

