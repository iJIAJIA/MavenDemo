package spoon;

import java.util.List;

public class CHMethod {
	
	private String name;
	
	private String decalaredClassName;
	
	private String remark;
	
	private List<CHMethod> bodyCallMethods;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CHMethod> getBodyCallMethods() {
		return bodyCallMethods;
	}

	public void setBodyCallMethods(List<CHMethod> bodyCallMethods) {
		this.bodyCallMethods = bodyCallMethods;
	}

	public String getDecalaredClassName() {
		return decalaredClassName;
	}

	public void setDecalaredClassName(String decalaredClassName) {
		this.decalaredClassName = decalaredClassName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\",\"decalaredClassName\":\""
				+ decalaredClassName + "\",\"remark\":\"" + remark
				+ "\",\"bodyCallMethods\":\"" + bodyCallMethods + "\"}";
	}
	
}
