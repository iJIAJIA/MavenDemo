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
//		执行String.intern()方法,如果常量池中存在当前字符串, 就会直接返回当前字符串. 如果常量池中没有此字符串, 会将此字符串放入常量池中后, 再返回。
//		jdk6由于常量池存放"永久区",jdk6会在永久区新建一个字符串对象
//		jdk7将常量池移到堆(heap)中,因此是在常量池新建一个该字符串的引用
		String s1 = new String("he") + new String("llo");
		s1.intern();
		String s2 = "hello";
		// 在jdk6里,这里为false;
	    // jdk7,这里为true,由于在s2声明之前,s1执行了intern方法,此时常量池里的"hello"为s1的引用,故s2的地址
	    // 等同于s1的地址
		System.out.println(s1 == s2); 
		
		String s3 = new String("wo") + new String("rld");
		String s4 = "world";
		s3.intern();
		System.out.println(s3 == s4); //这里都为false
	}
}
