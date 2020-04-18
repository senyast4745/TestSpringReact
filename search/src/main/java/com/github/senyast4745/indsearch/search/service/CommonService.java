package com.github.senyast4745.indsearch.search.service;

import com.github.senyast4745.indsearch.search.model.AbstractRedisEntity;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

public interface CommonService<E extends AbstractRedisEntity> {

    Mono<E> save(E entity);

    Mono<E> findById(@NonNull String s);

    Mono<E> findById(@NonNull Publisher<String> id);

    Mono<Void> deleteById(@NonNull String s);

    Mono<Void> deleteById(Publisher<String> id);

}
