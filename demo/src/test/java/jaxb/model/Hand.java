package jaxb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="hand")
public class Hand {
	
	private String size;
	
	private String color;
	
	private List<Finger> fingers;

	@XmlElement(name="size")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@XmlElement(name="fingers")
	public List<Finger> getFingers() {
		return fingers;
	}

	public void setFingers(List<Finger> fingers) {
		this.fingers = fingers;
	}
	
//	@XmlElement(name="color",nillable=true)
	@XmlElement(name="color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	public Hand(String size, String color) {
		super();
		this.size = size;
		this.color = color;
	}

	
	public Hand() {
		super();
	}

	@Override
	public String toString() {
		return "Hand [size=" + size + ", color=" + color + ", fingers="
				+ fingers + "]";
	}
	
	
	
}
