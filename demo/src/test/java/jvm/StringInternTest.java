package jvm;

import java.lang.reflect.Field;

import org.junit.Test;

public class StringInternTest {
	
	@Test
	public void testString() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		String hello = "hello word";
		String helloCopy = new String(hello.toCharArray());
		String helloCopy2 = new String(hello);
		System.out.println("helloCopy == hello: " + helloCopy == hello);
		System.out.println("helloCopy2 == hello: " + helloCopy2 == hello);
		Field value_field = String.class.getDeclaredField("value");
		value_field.setAccessible(true);
		char[] value = (char[]) value_field.get(hello);
		value[5] = '_';
//		String内部使用value字段对character数组进行存储,helloCopy2由于使用new String(String)进行实例化,内部还是对
//		hello对象的value的引用,因此更改hello的value值,会影响helloCopy2的值.而helloCopy使用new String(char[])进行实例化,
//		value是对hello.value的复制,Arrays.copyOf,因此不受影响
		System.out.println(hello);
		System.out.println(helloCopy);
		System.out.println(helloCopy2);
	}
	
	@Test
	public void testIntern() {
//		String.intern
		String s1 = new String("hello");
		s1.intern();
		String s2 = "hello";
		System.out.println(s1 == s2);
		
		String s3 = new String("world");
		String s4 = "world";
		s3.intern();
		System.out.println(s3 == s4);
		
		String s5 = "hello";
		String s6 = "hello";
		System.out.println(s5 == s6);
	}
}
