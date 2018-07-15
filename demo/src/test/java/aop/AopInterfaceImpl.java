package aop;

public class AopInterfaceImpl implements AopInterface{
	
	@Override
	public void print(String msg) {
		System.out.printf("msg: %s %n",msg);
	}
	
	public String getMsg(){
		return "msg";
	}
}
