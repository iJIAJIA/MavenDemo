package proxy.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.beanutils.PropertyUtilsBean;

import beancopy.FromBean;
import beancopy.ToBean;

public class CGLibProxyTest {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ShipProxy proxy = new ShipProxy();
		PropertyUtilsBean utilProxy = (PropertyUtilsBean) proxy.getProxy(PropertyUtilsBean.class);
		FromBean fb = new FromBean();
        fb.setAddress("北京市朝阳区大屯路");
        fb.setAge(20);
        fb.setMoney(30000.111);
        fb.setIdno("110330219879208733");
        fb.setName("测试");
        ToBean toBean = new ToBean();
		utilProxy.copyProperties(toBean, fb);
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		utilProxy.copyProperties(toBean, fb);
//		long startTime = System.currentTimeMillis();
//		PropertyUtils.copyProperties(toBean, fb);
//		System.out.println("cost:" + (System.currentTimeMillis() - startTime));
	}
	
	public static  class ShipProxy implements MethodInterceptor {
		// 通过Enhancer 创建代理对象
		private Enhancer enhancer = new Enhancer();

		// 通过Class对象获取代理对象
		public Object getProxy(Class c) {
			// 设置创建子类的类
			enhancer.setSuperclass(c);
			enhancer.setCallback(this);
			return enhancer.create();
		}
		
		

		@Override
		public Object intercept(Object obj, Method m, Object[] args,
				MethodProxy proxy) throws Throwable {
			long startTime = System.currentTimeMillis();
			Object invokeSuper = proxy.invokeSuper(obj, args);
			System.out.println(String.format("%s cost:%d", m.getName(),(System.currentTimeMillis()-startTime)));
			return invokeSuper;
		}
	}
}
