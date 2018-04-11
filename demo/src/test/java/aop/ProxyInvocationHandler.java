package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler{

	private ProxyInterface target;
	
	
	public ProxyInvocationHandler(ProxyInterface target){
		this.target = target;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("ProxyInvocationHandler invoke");
		return method.invoke(target, args);
	}
	
	
	public static void main(String[] args) {
		ProxyInterface f = (ProxyInterface) Proxy.newProxyInstance(ProxyInvocationHandler.class.getClassLoader(),
                new Class<?>[] { ProxyInterface.class },
                new ProxyInvocationHandler(new ProxyInterfaceImpl()));
		f.print("msg");
		System.out.println(f.toString());
		
	}
	
	
}
