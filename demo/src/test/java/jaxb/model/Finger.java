package jaxb.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="finger")
public class Finger {
	
	private String type;
	
	@XmlElement(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Finger(String type) {
		super();
		this.type = type;
	}

	@Override
	public String toString() {
		return "Finger [type=" + type + "]";
	}

	public Finger() {
		super();
	}
	
	
}
