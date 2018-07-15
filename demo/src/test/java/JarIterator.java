import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class JarIterator {
	
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		String path = "D:/下载/returnRefundMgtEar-prd-5.0.0.518832/lib";
		FileFilter fileFilter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().endsWith(".jar");
			}
		};
		File file = new File(path);
		File[] listFiles = file.listFiles(fileFilter);
		for(File jar: listFiles){
			URL url = new URL("file:" + jar.getPath());  
	        @SuppressWarnings("resource")
	        // 每个jar包设置自己独立的classloader
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, null);  
	        URL log4jProperties = urlClassLoader.getResource("log4j.properties");
	        if(log4jProperties != null){
	        	System.out.println(log4jProperties);
	        	
	        }
	        URL log4jXml = urlClassLoader.getResource("log4j.xml");
	        if(log4jXml != null){
	        	System.out.println(log4jXml);
	        	
	        }
	        
		}
	}
}
