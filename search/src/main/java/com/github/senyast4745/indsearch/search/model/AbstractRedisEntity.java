package com.github.senyast4745.indsearch.search.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractRedisEntity implements Serializable {

    private String id;

}
