package springaop;

public class Printor implements Printable {
	
	@Override
	public void print(String msg) {
		System.out.printf("print msg: %s %n",msg);
	}

	@Override
	public String toString() {
		return "Printor [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
