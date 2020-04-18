package com.github.senyast4745.indsearch.search.repository;

import com.github.senyast4745.indsearch.search.model.IndexEntry;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Repository
public class IndexRepository extends RedisRepository<IndexEntry>{

    public IndexRepository(ReactiveRedisTemplate<String, IndexEntry> redisTemplate) {
        super(redisTemplate, "index");
    }
}
