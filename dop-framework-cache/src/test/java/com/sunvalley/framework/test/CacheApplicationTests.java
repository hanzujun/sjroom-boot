package com.sunvalley.framework.test;

import com.sunvalley.framework.cache.autoconfig.RedisTemplateAutoConfigure;
import com.sunvalley.framework.cache.client.RedisOpsClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = Void.class)
@RunWith(SpringRunner.class)
@Import({
	RedisTemplateAutoConfigure.class,
	RedisAutoConfiguration.class,
	RedisOpsClient.class
})
public class CacheApplicationTests {

	@Autowired
	private RedisOpsClient redisOpsClient;

	@Test
	@Ignore
	public void test1() {
		String s = redisOpsClient.get("test1", 100, String.class, () -> "123123");
		Assert.assertEquals(s, "123123");
	}

	@Test
	@Ignore
	public void test2() {
		User user = new User(10001, "Dream", 30);
		User user1 = redisOpsClient.get("test2", 100, User.class, () -> user);
		Assert.assertEquals(user, user1);
	}

	@Test
	@Ignore
	public void test3() {
		User use1 = new User(10001, "Dream", 30);
		User use2 = new User(10002, "Dream", 30);
		List<User> list1 = Arrays.asList(use1, use2);
		List<User> list = redisOpsClient.getList("test3", 100, User.class, () -> list1);
		Assert.assertArrayEquals(list1.toArray(), list.toArray());
	}

	@Test
	@Ignore
	public void test4() {
		User use1 = new User(10001, "Dream", 30);
		User use2 = new User(10002, "Dream", 30);
		List<User> list1 = Arrays.asList(use1, use2);
		List<User> list = redisOpsClient.getList("dop:test","test4", 100, User.class, () -> list1);
		Assert.assertArrayEquals(list1.toArray(), list.toArray());
	}

	@Test
	@Ignore
	public void test5() {
		long result = redisOpsClient.ttl("dop:test5", "123123");
		Assert.assertEquals(result, -2);
	}

	@Test
	@Ignore
	public void test6() {
		User use = new User(10001, "Dream", null);
		Map<String, User> data = new HashMap<>();
		data.put("1", use);
		Map<String, User> datax = redisOpsClient.hmget("dop:test6:123123", User.class, () -> data, "1");
		System.out.println(datax);
	}

}
