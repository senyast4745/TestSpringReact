package com.github.senyast4745.indsearch.search.repository;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class RedisRepository<S extends AbstractRedisEntity> implements ReactiveCrudRepository<S, String> {

    private final String tableHashKey;

    private final ReactiveRedisTemplate<String, S> redisTemplate;

    private ReactiveHashOperations<String, String, S> reactiveHashOps;

    public RedisRepository(ReactiveRedisTemplate<String, S> redisTemplate, String tableHashKey) {
        this.redisTemplate = redisTemplate;
        this.tableHashKey = tableHashKey;
    }

    @PostConstruct
    private void init() {
        reactiveHashOps = redisTemplate.opsForHash();
    }

    @Override
    @NonNull
    public <S1 extends S> Mono<S1> save(@NonNull S1 entity) {
        return reactiveHashOps.put(tableHashKey, entity.getId(), entity).map(a -> entity);
    }

    @Override
    @NonNull
    public <S1 extends S> Flux<S1> saveAll(Iterable<S1> entities) {

        if (!entities.iterator().hasNext()) {
            return Flux.empty();
        }

        return reactiveHashOps.putAll(tableHashKey,
                StreamSupport.stream(entities.spliterator(), false)
                        .collect(Collectors.toMap(AbstractRedisEntity::getId, b -> b))
        ).thenMany(Flux.fromIterable(entities));
    }

    @Override
    @NonNull
    public <S1 extends S> Flux<S1> saveAll(@NonNull Publisher<S1> entityStream) {
        return Flux.from(entityStream).doOnEach(a -> {
            S1 el = a.get();
            if (a.hasError() || el == null || el.getId() == null) {
                throw new IllegalArgumentException();
            }
            reactiveHashOps.put(tableHashKey, el.getId(), el);
        });
    }

    @Override
    @NonNull
    public Mono<S> findById(@NonNull String s) {
        return reactiveHashOps.get(tableHashKey, s);
    }

    @Override
    @NonNull
    public Mono<S> findById(@NonNull Publisher<String> id) {
        return Mono.from(id).flatMap(a -> reactiveHashOps.get(tableHashKey, a));
    }

    @Override
    @NonNull
    public Mono<Boolean> existsById(@NonNull String s) {
        return reactiveHashOps.get(tableHashKey, s).map(Objects::isNull);
    }

    @Override
    @NonNull
    public Mono<Boolean> existsById(@NonNull Publisher<String> id) {
        return Mono.from(id).flatMap(a -> reactiveHashOps.get(tableHashKey, a)).map(Objects::isNull);
    }

    @Override
    @NonNull
    public Flux<S> findAll() {
        return reactiveHashOps.values(tableHashKey);
    }

    @Override
    @NonNull
    public Flux<S> findAllById(@NonNull Iterable<String> strings) {
        return Flux.fromIterable(strings).flatMap(a -> reactiveHashOps.get(tableHashKey, a));
    }

    @Override
    @NonNull
    public Flux<S> findAllById(@NonNull Publisher<String> idStream) {
        return Flux.from(idStream).flatMap(a -> reactiveHashOps.get(tableHashKey, a));
    }

    @Override
    @NonNull
    public Mono<Long> count() {
        return reactiveHashOps.size(tableHashKey);
    }

    @Override
    @NonNull
    public Mono<Void> deleteById(@NonNull String s) {
        return reactiveHashOps.remove(tableHashKey, s).then();
    }

    @Override
    @NonNull
    public Mono<Void> deleteById(@NonNull Publisher<String> id) {
        return Mono.from(id).doOnNext(a -> reactiveHashOps.remove(tableHashKey, a)).then();
    }

    @Override
    @NonNull
    public Mono<Void> delete(S entity) {
        return deleteById(entity.getId());
    }

    @Override
    @NonNull
    public Mono<Void> deleteAll(@NonNull Iterable<? extends S> entities) {
        return Flux.fromIterable(entities).doOnNext(a -> deleteById(a.getId())).then();
    }

    @Override
    @NonNull
    public Mono<Void> deleteAll(@NonNull Publisher<? extends S> entityStream) {
        return Flux.from(entityStream).doOnNext(a -> deleteById(a.getId())).then();
    }

    @Override
    @NonNull
    public Mono<Void> deleteAll() {
        return reactiveHashOps.delete(tableHashKey).then();
    }
}
