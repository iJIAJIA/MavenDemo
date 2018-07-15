package httpcomponent;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientPool {
	
	public static void main(String[] args) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(2);
		
		//调用前检查连接是否可用,最近一次调用至今时间超过指定的时间认为该连接不可用
		cm.setValidateAfterInactivity(3000);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 1);
		
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(3000)
			.setConnectTimeout(1000)
			.setSocketTimeout(1000)
			.build();
		
		
		
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .setDefaultRequestConfig(requestConfig)
		        .build();
		int maxRequest = 4;
		for(int i = 0; i < maxRequest; i++){
			Task task = new Task(httpClient);
			new Thread(task).start();
		}
	}
	
	@Test
	public void test(){
	}
	
	static class Task implements Runnable{
		
		private CloseableHttpClient httpclient;
		
		public Task(CloseableHttpClient httpClient){
			this.httpclient = httpClient;
		}
		
		@Override
		public void run() {
			try {
				HttpGet httpGet = new HttpGet("http://localhost:8080/" + Thread.currentThread().getName() +"/sayHey");
				CloseableHttpResponse execute = httpclient.execute(httpGet);
				System.out.println(EntityUtils.toString(execute.getEntity()));
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
			}
		}
	}
}
