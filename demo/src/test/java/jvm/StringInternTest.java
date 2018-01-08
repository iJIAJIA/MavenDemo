package jvm;

import java.lang.reflect.Field;

import org.junit.Test;

public class StringInternTest {
	
	@Test
	public void testIntern() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		String hello = "hello word";
		String helloCopy = new String(hello.toCharArray());
		System.out.println(helloCopy == hello);
		Field value_field = String.class.getDeclaredField("value");
		value_field.setAccessible(true);
		char[] value = (char[]) value_field.get(hello);
		value[5] = '_';
		System.out.println(hello);
		System.out.println(helloCopy);
	}
}
