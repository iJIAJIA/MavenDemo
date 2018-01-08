package spoon;

import java.util.List;

/**
 * call hierarchy target class
 * @author zhoujiaquan
 *
 */
public class CHClass {
	
	private String name;
	
	private List<CHMethod> method;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CHMethod> getMethod() {
		return method;
	}

	public void setMethod(List<CHMethod> method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\",\"method\":\"" + method + "\"}";
	}
	
	
	
	
}
