package ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

public class XmlConfigurationDemo {
	
	public static void main(String[] args) {
		XmlConfiguration config = new XmlConfiguration(XmlConfiguration.class.getResource("/ehcache/ehcache.xml"));
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(config);
		myCacheManager.init();
		
		Cache<String, String> cache = myCacheManager.getCache("foo", String.class, String.class);
		
		cache.put("key","blabla");
		String value = cache.get("key");
		System.out.println(value);
	}
}
