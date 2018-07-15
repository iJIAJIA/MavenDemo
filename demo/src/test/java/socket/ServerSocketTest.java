package socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(7454);
			System.out.println("wait client");
			Socket client = serverSocket.accept();
			System.out.println("get client");
			InputStream in = client.getInputStream();
			byte[] data = new byte[1024];
			while(in.read(data) != -1){
				System.out.println(new String(data,"utf-8"));
			}
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
