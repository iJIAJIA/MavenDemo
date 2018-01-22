package jvm;

/**
 * 静态分派
 * @author zhoujiaquan
 *
 */
public class StaticDispatch {

	static abstract class Human{
		
	}
	
	static class Woman extends Human{
		
	}
	
	static class Man extends Human{
		
	}
	
	public void sayHello(Human guy){
		System.out.println("hello guy");
	}
	
	public void sayHello(Woman woman){
		System.out.println("hello ladies");
	}
	
	public void sayHello(Man man){
		System.out.println("hello gentleman!");
	}
	
	public static void main(String[] args) {
		StaticDispatch staticDispatch = new StaticDispatch();
		Human man = new Man();
		Human women = new Woman();
		staticDispatch.sayHello(man);
		staticDispatch.sayHello(women);
		staticDispatch.sayHello((Man) man);
		staticDispatch.sayHello((Woman) women);
//		执行结果
		/*hello guy
		hello guy
		hello gentleman!
		hello ladies*/
	}
}
