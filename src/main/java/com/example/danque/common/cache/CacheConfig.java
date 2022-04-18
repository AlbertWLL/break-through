//package com.example.danque.common.cache;
//
//import com.example.danque.common.constants.RedisConstants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurer;
//import org.springframework.cache.interceptor.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//public class CacheConfig implements CachingConfigurer {
//
//	@Autowired
//    private RedisConnectionFactory factory;
//
//	@Bean
//	public RedisTemplate<String, Object> redisTemplate() {
//		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(factory);
//
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//
////        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
//
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
//        return redisTemplate;
//	}
//
//	@Bean
//	@Override
//	public CacheManager cacheManager() {
//
//		return RedisCacheManager.builder(factory)
//				.cacheDefaults(getCacheConfigurationWithTtl(redisTemplate(), 60 * 60))
//				.withCacheConfiguration(RedisConstants.CACHE_NAME, getCacheConfigurationWithTtl(redisTemplate(), 5 * 60))
//				.withCacheConfiguration(RedisConstants.CACHE_NAME, getCacheConfigurationWithTtl(redisTemplate(), 5 * 60))
//				.build();
//	}
//
//	RedisCacheConfiguration getCacheConfigurationWithTtl(RedisTemplate<String, Object> template, long seconds) {
//
//	    return RedisCacheConfiguration
//	            .defaultCacheConfig()
//	            // 设置key为String
//	            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
//	            // 设置value 为自动转Json的Object
//	            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
//	            // 不缓存null
//	            .disableCachingNullValues()
//	            // 缓存数据保存1小时
//	            .entryTtl(Duration.ofSeconds(seconds));
//	}
//
//	@Bean
//	@Override
//	public CacheResolver cacheResolver() {
//		return new SimpleCacheResolver(cacheManager());
//	}
//
//	@Bean
//	@Override
//	public KeyGenerator keyGenerator() {
//		return (o, method, objects) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append("global:jst-bms-charge-manage:");
//            sb.append(o.getClass().getName()).append(".");
//            sb.append(method.getName()).append(".");
//            for (Object obj : objects) {
//                sb.append(obj.toString());
//            }
//            return sb.toString();
//        };
//	}
//
//	@Bean
//	@Override
//	public CacheErrorHandler errorHandler() {
//		return new SimpleCacheErrorHandler();
//	}
//
//}
