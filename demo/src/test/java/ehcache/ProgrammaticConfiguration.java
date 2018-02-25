package ehcache;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class ProgrammaticConfiguration {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder
				.newCacheManagerBuilder()
				.withCache(
						"preConfigured",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(
								Long.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES))).build();
//		ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES)
//		管理堆缓存大小,只允许缓存10个key值
//		TODO 超过10个:缓存替换随机?
		cacheManager.init();
		Cache<Long, String> preConfigured = cacheManager.getCache(
				"preConfigured", Long.class, String.class);
		
		System.out.println(preConfigured.containsKey(1L));
		
		Cache<Long, Object> myCache = cacheManager
				.createCache("myCache", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, Object.class,
								ResourcePoolsBuilder.heap(10)));

		for(int i = 0;i <12;i++) {
			myCache.put((long)i, "da one!");
		}
		
		for (Iterator iterator = myCache.iterator(); iterator.hasNext();) {
			Entry<Long,String> type = (Entry<Long,String>) iterator.next();
			System.out.println(type.getKey());
//			
		}
		String value = (String) myCache.get(1L);
		System.out.println(value);
		
//		设置有效期
//		1.长期存活 不设置失效期的话即为长期存活
		CacheConfiguration<Long, String> expiryCacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
		        ResourcePoolsBuilder.heap(100)) 
//			2.从创建时间开始计算
		    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(20, TimeUnit.SECONDS)))
//		    3.从最近一次获取时间开始计算
		    .withExpiry(Expirations.timeToIdleExpiration(Duration.of(20, TimeUnit.SECONDS)))
		    .build();
		
		
		cacheManager.removeCache("preConfigured");

		cacheManager.close();
	}
	
}
