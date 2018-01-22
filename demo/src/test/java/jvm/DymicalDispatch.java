package jvm;

import jvm.StaticDispatch.Human;
import jvm.StaticDispatch.Man;
import jvm.StaticDispatch.Woman;

/**
 * 动态分配
 * 
 * @author zhoujiaquan
 *
 */
public class DymicalDispatch {

	static abstract class Human {
		public void sayHello() {
			System.out.println("hello guy");
		}
	}

	static class Woman extends Human {
		public void sayHello() {
			System.out.println("hello guy");
		}
	}

	static class Man extends Human {
		public void sayHello() {
			System.out.println("hello gentleman!");
		}
	}


	public static void main(String[] args) {
		Human man = new Man();
		Human women = new Woman();
		man.sayHello();
		women.sayHello();
	}
}
