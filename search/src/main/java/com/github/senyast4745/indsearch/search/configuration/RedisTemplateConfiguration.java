package com.github.senyast4745.indsearch.search.configuration;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public abstract class RedisTemplateConfiguration<S extends AbstractRedisEntity> {
    @Bean
    public ReactiveRedisTemplate<String, S> initRedisCustomOperation(ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<S> serializer = new Jackson2JsonRedisSerializer<>(getResourceClass());
        RedisSerializationContext.RedisSerializationContextBuilder<String, S> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());

//        RedisSerializationContext<String, S> serializationContext = builder.value(serializer).build();
        RedisSerializationContext<String, S> serializationContext = builder.hashKey(new StringRedisSerializer())
                .hashValue(serializer).build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    protected abstract Class<S> getResourceClass();

}
