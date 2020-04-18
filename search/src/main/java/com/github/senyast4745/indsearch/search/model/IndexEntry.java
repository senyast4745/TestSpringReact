package com.github.senyast4745.indsearch.search.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IndexEntry extends AbstractRedisEntity {

    private String name;
}
