package com.github.senyast4745.indsearch.search.controller;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

public interface CommonController<E extends AbstractRedisEntity> {

    @PostMapping
    Mono<E> save(@RequestBody E entity);

    @GetMapping("{id}")
    Mono<E> findById(@PathVariable String id);

    @DeleteMapping("{id}")
    Mono<Void> deleteById(@PathVariable String id);

}