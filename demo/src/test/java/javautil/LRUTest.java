package javautil;

import java.util.LinkedHashMap;

import org.junit.Test;

public class LRUTest {
	
	@Test
	public void test() {
		LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>(16, 0.75f,true);
		linkedHashMap.put("a", "value-a");
		linkedHashMap.put("b", "value-b");
		linkedHashMap.put("c", "value-c");
		System.out.println(linkedHashMap.get("a"));
	}
}
