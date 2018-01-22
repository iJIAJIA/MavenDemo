package jvm;

public class ExtendTest  extends c1 implements i1,i2{

//	@Override
	/*public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("just do it");
	}*/
	
	
	public void doSomething(String order){
		System.out.println(order);
	}
	
	public static void main(String[] args) {
		i1 i1 = new ExtendTest();
		i2 i2 = new ExtendTest();
		c1 c1 = new ExtendTest();
		ExtendTest extendTest = new ExtendTest();
		i1.doSomething();
		i2.doSomething();
		c1.doSomething();
		extendTest.doSomething();
		ExtendTest.doSomethingStatic();
		ExtendTest.doSomethingStatic("");
	}
	
	public static void doSomethingStatic(){
		
	}
	public static void doSomethingStatic(String order){
		
	}
	
}

class c1{
	public void doSomething(){System.out.println("don't do it");};
}

interface i1{
	void doSomething();
}
interface i2{
	void doSomething();
}