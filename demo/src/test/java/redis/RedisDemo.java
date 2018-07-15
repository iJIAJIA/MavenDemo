package redis;

import redis.clients.jedis.Jedis;

public class RedisDemo {
	
	public static void main(String[] args) {
		String keyPrefix = "JedisDemo:";
		Jedis jedis = new Jedis ("118.89.106.225",6379);
//		for(int i = 0; i < 10; i++){
//			String setResult = jedis.set("JedisDemo:" + i, "i");
////			0's setResult: OK
//			System.out.println(i + "'s setResult: " + setResult);
//		}
//		key值模糊查询
//		Set<String> matchKeySet = jedis.keys(keyPrefix + "*");
//		System.out.println("matchKeySet: " + matchKeySet);
//		批量删除
//		Long batchDelResult = jedis.del(matchKeySet.toArray(new String[matchKeySet.size()]));
//		System.out.println("batchDelResult: " + batchDelResult);
		
		Long expireResult = jedis.expire(keyPrefix + "exits", 10); //seconds
		// expireResult for not exits key: 0 不存在时返回0
		System.out.println("expireResult for not exits key: " + expireResult);
		jedis.set(keyPrefix + "exits", "10");
		// expireResult for exits key: 1
		expireResult = jedis.expire(keyPrefix + "exits", 10); //seconds
		System.out.println("expireResult for exits key: " + expireResult);
		// setexResult: OK
		// 0和-1都是非法值
		String setexResult = jedis.setex(keyPrefix + "setex", 1, "10");
		System.out.println("setexResult: " + setexResult);
		
		// 

		//		Long setnxResult = jedis.setnx(key, "blabla");
//		System.out.println(setnxResult);
//		if(setnxResult == 0){
//			Long delResult = jedis.del(key);
//			System.out.println("delResult: " + delResult);
//		}
		jedis.close();
	}
}
