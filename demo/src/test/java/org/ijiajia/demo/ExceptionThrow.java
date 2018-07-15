package org.ijiajia.demo;

public class ExceptionThrow {
	
	public void doThrow() throws NumberFormatException{
		throw new NumberFormatException();
	}
	
	public void catchAndThrow() {
		try{
			
			doThrow();
		}catch(Exception e){
			throw e;
		}
	}
	
	public static void main(String[] args) {
		new ExceptionThrow().catchAndThrow();
	}
}
