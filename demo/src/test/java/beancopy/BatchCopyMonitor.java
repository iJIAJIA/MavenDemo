package beancopy;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;

public class BatchCopyMonitor {

	
	private int copyCount;
	
	public BatchCopyMonitor(int copyCount) {
		super();
		this.copyCount = copyCount;
	}


	public void doBatchCopy(IMethodCopy methodCopy,FromBean orign){
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < copyCount; i++){
			methodCopy.copyBean(orign);
		}
		System.out.println(String.format("%5s %20s %s", "copy数","方法名称","耗时"));
		System.out.println(String.format("%5s %20s cost:%d",copyCount, methodCopy.getMethodName(),(System.currentTimeMillis() - startTime)));
	}
	
	
	public void setCopyCount(int copyCount) {
		this.copyCount = copyCount;
	}


	public static void main(String[] args) {
		FromBean fb = new FromBean();
        fb.setAddress("北京市朝阳区大屯路");
        fb.setAge(20);
        fb.setMoney(30000.111);
        fb.setIdno("110330219879208733");
        fb.setName("测试");
		
		IMethodCopy springcopy = new IMethodCopy() {
			
			@Override
			public String getMethodName() {
				// TODO Auto-generated method stub
				return "spring.beanutils";
			}
			
			@Override
			public ToBean copyBean(FromBean orign) {
				ToBean toBean = new ToBean();
				BeanUtils.copyProperties(orign, toBean);
				return toBean;
			}
		};
		
		IMethodCopy apacheBeanUtilsCopy = new IMethodCopy() {
			
			@Override
			public String getMethodName() {
				// TODO Auto-generated method stub
				return "apache.beanutils";
			}
			
			@Override
			public ToBean copyBean(FromBean orign) {
				ToBean toBean = new ToBean();
				try {
					org.apache.commons.beanutils.BeanUtils.copyProperties(toBean, orign);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return toBean;
			}
		};
		
		IMethodCopy apachePropertyUtilsCopy = new IMethodCopy() {
			
			@Override
			public String getMethodName() {
				// TODO Auto-generated method stub
				return "apache.propertyUtils";
			}
			
			@Override
			public ToBean copyBean(FromBean orign) {
				ToBean toBean = new ToBean();
				try {
					org.apache.commons.beanutils.PropertyUtils.copyProperties(toBean, orign);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return toBean;
			}
		};
		
		BatchCopyMonitor batchCopyMonitor = new BatchCopyMonitor(1);
		batchCopyMonitor.doBatchCopy(springcopy, fb);
//		batchCopyMonitor.doBatchCopy(apacheBeanUtilsCopy, fb);
		batchCopyMonitor.doBatchCopy(apachePropertyUtilsCopy, fb);
		
		batchCopyMonitor.setCopyCount(10);
		batchCopyMonitor.doBatchCopy(springcopy, fb);
//		batchCopyMonitor.doBatchCopy(apacheBeanUtilsCopy, fb);
		batchCopyMonitor.doBatchCopy(apachePropertyUtilsCopy, fb);
		
		batchCopyMonitor.setCopyCount(10000);
		batchCopyMonitor.doBatchCopy(springcopy, fb);
//		batchCopyMonitor.doBatchCopy(apacheBeanUtilsCopy, fb);
		batchCopyMonitor.doBatchCopy(apachePropertyUtilsCopy, fb);
		
	}
}
