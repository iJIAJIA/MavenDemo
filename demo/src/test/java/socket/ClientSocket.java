package socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("127.0.0.1", 7454));
		System.out.println("connected");
		OutputStream out = socket.getOutputStream();
		out.write("bitch".getBytes());
		socket.close();
	}
}
