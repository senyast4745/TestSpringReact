package com.github.senyast4745.indsearch.search.service;

import com.github.senyast4745.indsearch.search.model.IndexEntry;
import com.github.senyast4745.indsearch.search.repository.IndexRepository;
import org.springframework.stereotype.Service;

@Service
public class IndexService extends AbstractService<IndexEntry, IndexRepository> {

    public IndexService(IndexRepository repository) {
        super(repository);
    }
}
