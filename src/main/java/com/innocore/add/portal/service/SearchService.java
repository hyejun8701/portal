package com.innocore.add.portal.service;

import com.google.common.collect.Lists;
import com.innocore.add.portal.repository.SearchRepository;
import com.innocore.add.portal.vo.Info;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Object search(String indices, Map<String, Object> query, Map<String, SortOrder> sort) {
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        for(String key: query.keySet()) {
            searchSourceBuilder.query(QueryBuilders.matchQuery(key, query.get(key)));
        }

        for(String key: sort.keySet()) {
            searchSourceBuilder.sort(new FieldSortBuilder(key).order(sort.get(key)));
        }

        searchRequest.source(searchSourceBuilder);

        List<Map<String, Object>> list = new ArrayList<>();

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //System.out.println(searchResponse);

            SearchHits searchHits = searchResponse.getHits();

            for(SearchHit hit : searchHits) {
                System.out.println(hit);

                Map<String, Object> map = hit.getSourceAsMap();
                map.put("_id", hit.getId());
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Info> findAll() {
        return Lists.newArrayList(searchRepository.findAll());
    }

    public List<Info> findByOs(String os) {
        return searchRepository.findByOs(os);
    }
}
