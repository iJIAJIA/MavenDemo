package aop;

public class ProxyInterfaceImpl implements ProxyInterface{
	
	@Override
	public void print(String msg) {
		System.out.printf("print msg: %s %n",msg);
	}

	@Override
	public String toString() {
		return "ProxyInterfaceImpl [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
