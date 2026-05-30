import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageImpl
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair

@Configuration
@EnableCaching
class RedisConfig {

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
        // KotlinModule() kullanımı güncel Jackson sürümlerinde otomatik yapılandırıcı ile desteklenir
        val objectMapper = ObjectMapper().registerModule(KotlinModule())

        // Güncel Spring Boot sürümlerinde objectMapper doğrudan constructor içinde verilir
        val jsonSerializer = Jackson2JsonRedisSerializer(objectMapper, PageImpl::class.java)

        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(SerializationPair.fromSerializer(jsonSerializer))

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        val redisTemplate = RedisTemplate<String, Any>()

        redisTemplate.setConnectionFactory(redisConnectionFactory)
        redisTemplate.keySerializer = StringRedisSerializer()

        // Buradaki hata riskini önlemek için de yukarıdaki gibi objectMapper'ı ekledik
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(objectMapper, PageImpl::class.java)

        return redisTemplate
    }
}
