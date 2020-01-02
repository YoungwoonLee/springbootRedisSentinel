package com.sentinel.engis.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StopWatch;

@Configuration
@EnableRedisRepositories
public class RedisSentinelConfig {

	@Autowired
	private RedisSentinelConfigProperties sentinelProperties;
	@Autowired 
	private RedisConnectionFactory factory;
	
//	@Value("${spring.redis.host}")
//    private String redisHost;
//    @Value("${spring.redis.port}")
//    private int redisPort;

	@Bean
	public StringRedisTemplate redisTemplate() {
		return new StringRedisTemplate(lettuceConnectionFactory());
//		return new StringRedisTemplate(jedisConnectionFactory());
	}
/**	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){

		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration( sentinelProperties.getMaster(), sentinelProperties.getNodes());
		sentinelConfig.setPassword( RedisPassword.of( sentinelProperties.getPassword() ) );
		JedisConnectionFactory jcf = new JedisConnectionFactory( sentinelConfig );

		return jcf;
	}
*/
	@Bean
	public RedisConnectionFactory lettuceConnectionFactory() {

		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration( sentinelProperties.getMaster(), sentinelProperties.getNodes());
		sentinelConfig.setPassword( RedisPassword.of( sentinelProperties.getPassword() ) );
		LettuceConnectionFactory lcf = new LettuceConnectionFactory(sentinelConfig, LettuceClientConfiguration.defaultConfiguration() );
		
		return lcf;
	}

//    @Bean
//    public RedisTemplate<?, ?> redisTemplate() {
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        
//        //Serializer 설정
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//
//        redisTemplate.setConnectionFactory(connectionFactory());
//        return redisTemplate;
//    }
/*
//	@Bean  //@~~@~~tmp
	public RedisSentinelConfiguration sentinelConfig() {
		RedisSentinelConfiguration sentinelConfig = 
						new RedisSentinelConfiguration()
						.master("mymaster")
						.sentinel("192.168.20.233", 11001)
						.sentinel("192.168.20.233", 11002)
						.sentinel("192.168.20.233", 11003);

//		RedisNode node1 = new RedisNode("192.168.20.233", 11001);
//		RedisNode node2 = new RedisNode("192.168.20.233", 11002);
//		RedisNode node3 = new RedisNode("192.168.20.233", 11003);
//
//		List<RedisNode> sentinels = new ArrayList<>();
//		sentinels.add(node1);
//		sentinels.add(node2);
//		sentinels.add(node3);
//		
//		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
//		sentinelConfig.setSentinels(sentinels);

		sentinelConfig.setMaster("mymaster");
		sentinelConfig.setPassword( RedisPassword.of("engis9090") );
		
		return sentinelConfig;
	}
*/
	/**
	 * Clear database before shut down.
	 */
//	@PreDestroy
//	public void flushTestDb() {
//		factory.getConnection().flushDb();
//	}
}
