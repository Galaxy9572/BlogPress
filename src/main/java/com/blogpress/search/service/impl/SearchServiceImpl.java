package com.blogpress.search.service.impl;

import com.blogpress.search.bean.entity.SearchUser;
import com.blogpress.search.service.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * 搜索服务实现类
 * @author JY
 */
@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {

    private final ElasticsearchOperations esOperations;

    @Autowired
    public SearchServiceImpl(ElasticsearchOperations esOperations) {
        this.esOperations = esOperations;
    }

    public void search() {
        Criteria criteria = new Criteria("nick").is("ljy");
        Query query = new CriteriaQuery(criteria);
        SearchHits<SearchUser> hits = esOperations.search(query, SearchUser.class);
        long totalHits = hits.getTotalHits();
    }

}
