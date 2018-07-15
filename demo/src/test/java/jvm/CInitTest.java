package jvm;

public class CInitTest {
	
	
	static{
		result = "aa";
		System.out.println("static");
		
	}
	static final Object initHolder = init(); 
			
	static Object init(){
		System.out.println("init");
//		result = "bb";
		System.out.println(result);
		return new Object();
	}
	
	static String result;
	
	
}
