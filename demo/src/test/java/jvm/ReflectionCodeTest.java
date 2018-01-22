package jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionCodeTest {
	
	private void doSomething(){
		System.out.println("haha");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class clazz = ReflectionCodeTest.class;
		Method method = clazz.getDeclaredMethod("doSomething");
		method.invoke(clazz.newInstance());
	}
}
