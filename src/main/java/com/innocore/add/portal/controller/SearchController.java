package com.innocore.add.portal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innocore.add.portal.service.SearchService;
import com.innocore.add.portal.vo.Info;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping
    public List<Info> list() {
        return searchService.findAll();
    }

    @GetMapping("/search")
    public Object search(@RequestBody Info info) {
        String indices = "system";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, Object> query = objectMapper.convertValue(info, Map.class);

        for(String key: query.keySet()) {
            System.out.println(key + " : " + query.get(key));
        }

        Map<String, SortOrder> sort = new HashMap<>();
        sort.put("_id", SortOrder.DESC);

        return searchService.search(indices, query, sort);
    }
}
