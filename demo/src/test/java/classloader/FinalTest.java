
package classloader;

import java.util.concurrent.TimeUnit;

/**
 * 编程过程中经常用的static，final关键字，也经常听到同事谈起这些词，到底他们怎么样呢，jvm中class类文件结构可以找到答案。

	ConstantValue属性
	
	ConstantValue属性的作用是通知虚拟机自动为静态变量赋值，只有被static修饰的变量才可以使用这项属性。非static类型的变量的赋值是在实例构造器方法中进行的；tatic类型变量赋值分两种，在类构造其中赋值，或使用ConstantValue属性赋值。
	
	在实际的程序中，只有同时被final和static修饰的字段才有ConstantValue属性，且限于基本类型和String。编译时Javac将会为该常量生成ConstantValue属性，在类加载的准备阶段虚拟机便会根据ConstantValue为常量设置相应的值，如果该变量没有被final修饰，或者并非基本类型及字符串，则选择在类构造器中进行初始化。
	
	为什么ConstantValue的属性值只限于基本类型和string？
	因为从常量池中只能引用到基本类型和String类型的字面量
	
	final、static、static final修饰的字段赋值的区别?
	static修饰的字段在加载过程中准备阶段被初始化，但是这个阶段只会赋值一个默认的值（0或者null而并非定义变量设置的值）初始化阶段在类构造器中才会赋值为变量定义的值。
	final修饰的字段在运行时被初始化，可以直接赋值，也可以在实例构造器中赋值，赋值后不可修改。
	static final修饰的字段在javac编译时生成comstantValue属性，在类加载的准备阶段直接把constantValue的值赋给该字段。 
	可以理解为在编译期即把结果放入了常量池中。
 * @author zhoujiaquan
 *
 */
public class FinalTest {
	
	public static final String FINAL_CONSTANT_ORDER_BEFORE_STATIC_CODE_BLOCK = print("FINAL_CONSTANT_ORDER_BEFORE_STATIC_CODE_BLOCK");
	
	public static String NOT_FINAL_CONSTANT_ORDER_BEFORE_STATIC_CODE_BLOCK = print("NOT_FINAL_CONSTANT_ORDER_BEFORE_STATIC_CODE_BLOCK");
	
	static{
		/**
		 * 静态变量和静态代码块的执行时按照声明顺序执行的
		 */
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("class initialize");
	}
	
	public static String NOT_FINAL_CONSTANT_ORDER_AFTER_STATIC_CODE_BLOCK = print("NOT_FINAL_CONSTANT_ORDER_AFTER_STATIC_CODE_BLOCK");
	
	public static final String FINAL_CONSTANT_ORDER_AFTER_STATIC_CODE_BLOCK = print("FINAL_CONSTANT_ORDER_AFTER_STATIC_CODE_BLOCK");
	
	public static String print(String name){
		try {
			System.out.println(name + " is null?:" + FinalTest.class.getField(name).get(null));
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "print";
	}
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(FinalTest.NOT_FINAL_CONSTANT_ORDER_AFTER_STATIC_CODE_BLOCK);
				
			}
		}).start();
//		System.out.println(FinalTest.COSNTANT);
	}
}
