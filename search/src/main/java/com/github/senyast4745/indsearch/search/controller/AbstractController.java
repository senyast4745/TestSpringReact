package com.github.senyast4745.indsearch.search.controller;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import com.github.senyast4745.indsearch.search.service.CommonService;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public abstract class AbstractController<E extends AbstractRedisEntity, S extends CommonService<E>>
        implements CommonController<E> {

    private final S service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    public Mono<E> save(@RequestBody E entity) {
        return service.save(entity);
    }

    @Override
    public Mono<E> findById(String id) {
        return service.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return service.deleteById(id);
    }
}