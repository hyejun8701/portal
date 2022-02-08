package com.innocore.add.portal.repository;

import com.innocore.add.portal.vo.Info;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Info, String> {
    List<Info> findByOs(String os);
}
