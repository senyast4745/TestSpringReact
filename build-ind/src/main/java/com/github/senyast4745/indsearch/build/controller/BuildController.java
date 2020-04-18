package com.github.senyast4745.indsearch.build.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.senyast4745.indsearch.build.configuration.AMPQConfig;
import com.github.senyast4745.indsearch.build.model.SimpleModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@RestController
public class BuildController {

    private final Sender sender;

    private final ObjectMapper objectMapper;

    public BuildController(Sender sender, ObjectMapper objectMapper) {
        this.sender = sender;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public Mono<String> sendTest() {
        return sender.send(Flux.just(new SimpleModel("hello", "rabbitMQ")).map(i -> {
                    try {
                        return new OutboundMessage("", AMPQConfig.QUEUE, objectMapper.writeValueAsBytes(i));
                    } catch (JsonProcessingException e) {
                        return new OutboundMessage("", AMPQConfig.QUEUE, e.getMessage().getBytes());
                    }
                }
        )).then(Mono.just("yoooooy"));
    }

}
