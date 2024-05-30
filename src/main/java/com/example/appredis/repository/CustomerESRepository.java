package com.example.appredis.repository;

import com.example.appredis.model.ESCustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerESRepository extends ElasticsearchRepository<ESCustomer, Long> {
}
