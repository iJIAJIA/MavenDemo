package bridgemethod;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.validation.annotation.Validated;

public class BridgeMethodTest {
	
	@Test
	public void testWhichIsBridgeMethod() {
		Method[] declaredMethods = BridgeMethodImpl.class.getDeclaredMethods();
		for(Method method: declaredMethods) {
			System.out.printf("%s:%s:%b\r\n",method.getName(),method.getGenericReturnType().getTypeName(),method.isBridge());
			/*
			 * doSomething:java.lang.String:false
			 * doSomething:java.lang.Object:true
			 */
		}
	}
	
	/**
	 * 根据接口方法获取实现类方法
	 */
	@Test
	public void testGetMethodFromInterfaceImpl() throws Exception{
		// 可以根据接口方法信息获取对应实现类的实现方法
		// 但由于jdk1.5泛型方法的兼容特性,此时获取的方法为编译器生成的桥接方法信息
		// 如果需要获取原始方法的一些信息(如方法上的注解类),则需要注意此场景.桥接方法并没有原始方法的注解类
		Method[] methods = IBridgeMethodTest.class.getMethods();
		Method interfaceMethod = methods[0];
		Method implMethod = BridgeMethodImpl.class.getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
		// doSomething:java.lang.Object:true泛型的默认类型为Object
		System.out.printf("%s:%s:%b\r\n",implMethod.getName(),implMethod.getGenericReturnType().getTypeName(),implMethod.isBridge());
		// 桥接方法是否有原始实现方法的注解
		Validated declaredAnnotation = implMethod.getDeclaredAnnotation(Validated.class);
		// 桥接方法是否有原始实现方法的注解:false
		System.out.println("桥接方法是否有原始实现方法的注解:" + (declaredAnnotation == null));
	}
}
