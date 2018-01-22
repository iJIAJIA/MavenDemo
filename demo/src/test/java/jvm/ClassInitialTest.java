
package jvm;

public class ClassInitialTest{
	
	private static int A = 1;
	
	{
		System.out.println("not static: " + A);
	}
	
	static {
		System.out.println("static: " + A);
		A=2;
		System.out.println("static: " + A);
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
//		Class.forName("jvm.ClassInitialTest");
		new ClassInitialTest();
	}
}