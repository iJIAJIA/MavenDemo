package beancopy;

public interface IMethodCopy {
	
	ToBean copyBean(FromBean orign);
	
	String getMethodName();
}
