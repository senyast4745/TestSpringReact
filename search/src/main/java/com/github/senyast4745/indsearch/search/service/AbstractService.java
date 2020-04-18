package com.github.senyast4745.indsearch.search.service;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import com.github.senyast4745.indsearch.search.repository.RedisRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public abstract class AbstractService <E extends AbstractRedisEntity, R extends RedisRepository<E>> implements CommonService<E>{

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public Mono<E> save(E entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<E> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<E> findById(Publisher<String> id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String s) {
        return repository.deleteById(s);
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return repository.deleteById(id);
    }
}
