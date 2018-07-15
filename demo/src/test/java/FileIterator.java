import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.BeforeClass;
import org.junit.Test;


public class FileIterator {
	
	private String rootPath = "D:/ULE/项目资料/售后服务/静态资源迁移/售后服务静态资源整理V1.0";
	
	private String urlPath = "https://i0.ulecdn.com/removal/service/";
	
	private static CloseableHttpClient httpClient;
	
	@BeforeClass
	public static void init(){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(1);
		cm.setDefaultMaxPerRoute(1);
		
		RequestConfig requestConfig = RequestConfig.custom()
			.build();
		
		httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .setDefaultRequestConfig(requestConfig)
		        .build();
	}
	
	@Test
	public void test() throws ClientProtocolException, IOException{
		File file = new File("D:/ULE/项目资料/售后服务/静态资源迁移/售后服务静态资源整理V1.0");
		iterator(file);
	}
	
	public void iterator(File file) throws ClientProtocolException, IOException{
		File[] listFiles = file.listFiles();
		for(File childFile: listFiles) {
			if(childFile.isDirectory()){
				iterator(childFile);
			}else if(childFile.isFile()){
				validateURL(childFile.getPath());
			}
		}
	}
	
	private void validateURL(String path) throws ClientProtocolException, IOException{
		String url = path.replace("\\", "/").replace(rootPath, urlPath);
		CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != 200){
			System.out.println(String.format("status code:%d,url:%s", statusCode,url));
		}
		EntityUtils.consumeQuietly(response.getEntity());
	}
	
}
