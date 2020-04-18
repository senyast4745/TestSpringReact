package com.github.senyast4745.indsearch.search.controller;

import com.github.senyast4745.indsearch.search.model.IndexEntry;
import com.github.senyast4745.indsearch.search.service.IndexService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
public class IndexController extends AbstractController<IndexEntry, IndexService>{

    protected IndexController(IndexService service) {
        super(service);
    }
}
