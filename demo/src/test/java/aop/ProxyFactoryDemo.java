package aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryDemo {
	
	public static void main(String[] args) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new AopInterfaceImpl());
		proxyFactory.setInterfaces(AopInterface.class);
		proxyFactory.addAdvice(new BeforeAndAfterAdvice());
		proxyFactory.addAdvice(new BeforeAndAfterAdviceII());
		AopInterface proxy = (AopInterface) proxyFactory.getProxy();
		proxy.print("傻逼赵忠亚");
	}
	
	
	static class BeforeAndAfterAdvice implements MethodBeforeAdvice,AfterReturningAdvice,ThrowsAdvice{
		
		public void afterThrowing(Exception ex){
			System.out.println("afterThrowing invoke..");
			System.out.println(ex.getMessage());
		}
		
		@Override
		public void afterReturning(Object arg0, Method arg1, Object[] arg2,
				Object arg3) throws Throwable {
			System.out.println("afterReturning invoke...");
		}
		
		@Override
		public void before(Method arg0, Object[] arg1, Object arg2)
				throws Throwable {
			// TODO Auto-generated method stub
			System.out.println("before invoke...");
		}
	}
	
	static class BeforeAndAfterAdviceII implements MethodBeforeAdvice,AfterReturningAdvice{
		
		@Override
		public void afterReturning(Object arg0, Method arg1, Object[] arg2,
				Object arg3) throws Throwable {
			System.out.println("BeforeAndAfterAdviceII afterReturning invoke...");
		}
		
		@Override
		public void before(Method arg0, Object[] arg1, Object arg2)
				throws Throwable {
			// TODO Auto-generated method stub
			System.out.println("BeforeAndAfterAdviceII before invoke...");
			throw new Exception("afterReturning throw");
		}
	}
}
