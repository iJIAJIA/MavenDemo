package springaop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyFactoryDemo {
	
	public static void main(String[] args) {
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new Printor());
		proxyFactory.addAdvice(new AdviceDemo());
		Printable proxy = (Printable) proxyFactory.getProxy();
//		是否冻结配置
//		proxyFactory.setFrozen(true);
		proxyFactory.addAdvice(new AroundDemo());
		proxy.print("傻逼周嘉全");
		System.out.println(proxy);
		System.out.println(proxy.hashCode());
//		ProxyFactory proxyFactory = new ProxyFactory();
//		proxyFactory.setTarget(new Printor());
//		Printable proxy = (Printable) proxyFactory.getProxy();
//		proxy.print("傻逼周嘉全");
	}
	
	static class AdviceDemo implements MethodBeforeAdvice,AfterReturningAdvice{
		
		long startTime;
		
		@Override
		public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
			System.out.printf("%s end,cost time: %d %n",method.getName(),(System.currentTimeMillis()-startTime));
		}
		
		@Override
		public void before(Method method, Object[] args, Object target) throws Throwable {
			// TODO Auto-generated method stub
			System.out.printf("%s invoke... %n",method.getName());
			startTime = System.currentTimeMillis();
			
		}
	}
	
	// 环绕
	static class AroundDemo implements MethodInterceptor{
		
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
//			Object[] arguments = invocation.getArguments();
			String methodName = invocation.getMethod().getName();
			long startTime = System.currentTimeMillis();
			Object invokeResult = invocation.proceed();
			System.out.printf("AroundDemo %s end,cost time: %d %n",methodName,(System.currentTimeMillis()-startTime));
			return invokeResult;
		}
	}
	
}
