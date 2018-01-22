package jvm.gc;

public class GCTest {
	
	public static void main(String[] args) {
		byte[] placeHolder = new byte[1024*64];
		System.gc();
	}
}
