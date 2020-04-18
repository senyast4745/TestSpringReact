package com.github.senyast4745.indsearch.search.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.senyast4745.indsearch.search.model.IndexEntry;
import com.github.senyast4745.indsearch.search.repository.IndexRepository;
import com.rabbitmq.client.Delivery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class AMPQController {

    private final IndexRepository repository;

    private final ObjectMapper mapper;

    private final
    Flux<Delivery> deliveryFlux;

    public AMPQController(Flux<Delivery> deliveryFlux, ObjectMapper mapper, IndexRepository repository) {
        this.deliveryFlux = deliveryFlux;
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    private void subscribe() {
        deliveryFlux.subscribe(c -> {
            try {
                byte [] body = c.getBody();
                log.info("Received {}", new  String(body));
                repository.save(mapper.readValue(body, IndexEntry.class));
            } catch (IOException e) {
                log.warn("can not parse message from queue", e);
            }
        });
    }
}
