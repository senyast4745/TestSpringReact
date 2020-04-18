package com.github.senyast4745.indsearch.search.configuration;

import com.github.senyast4745.indsearch.search.model.IndexEntry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexEntryConfig extends RedisTemplateConfiguration<IndexEntry> {

    @Override
    protected Class<IndexEntry> getResourceClass() {
        return IndexEntry.class;
    }
}
