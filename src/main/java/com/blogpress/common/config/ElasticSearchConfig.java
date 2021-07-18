package com.blogpress.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elastic Search配置
 * @author JY
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.blogpress.search.dao")
public class ElasticSearchConfig {
}
