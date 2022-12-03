package dev.eltonsandre.sample.infra.cache.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableConfigurationProperties({RedisProperties.class, CacheConfigProperties.class})
public class RedisConfiguration implements CachingConfigurer {

    private final RedisProperties redisProperties;
    private final CacheConfigProperties cacheConfigProperties;

    @Bean
    RedisCacheConfiguration defaultCacheConfiguration(ObjectMapper objectMapper) {
        log.info("I=Aplicando customização de serialização");

        final var objectMapperCopy = objectMapper.copy()
                .activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30))
                .disableCachingNullValues()
                .serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapperCopy)));
    }

    @Bean
    RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer(ObjectMapper objectMapper) {
        log.info("I=Aplicando customização de TTL e config por cache name");

        return builder -> builder.withInitialCacheConfigurations(
                        this.cacheConfigProperties.getCaches()
                                .entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                        value -> defaultCacheConfiguration(objectMapper)
                                                .entryTtl(value.getValue().getTimeToLive())))
                )
                // Config de cache customizada para algo especifico
                .withCacheConfiguration("custom-cache", RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(10)));
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheClearError(final RuntimeException exception, final Cache cache) {
                log.error("errorHandler, exception: {}, cache: {}", exception.getMessage(), cache.getName());
            }

            @Override
            public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object key) {
                log.error("errorHandler, exception: {}, cache: {}, key: {}", exception.getMessage(), cache.getName(), key);
            }

            @Override
            public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object key) {
                log.error("errorHandler, exception: {}, cache: {}, key: {}", exception.getMessage(), cache.getName(), key);
            }

            @Override
            public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object key, final Object value) {
                log.error("errorHandler, exception: {}, cache: {}, key: {}, value: {}", exception.getMessage(), cache.getName(), key, value);
            }

        };
    }

}
