package com.example.appredis.service;

import com.example.appredis.model.Customer;
import com.example.appredis.model.ESCustomer;

import java.util.List;

public interface CustomerServices {
    // database Functions
    List<Customer> getAllCustomer();

    Customer getCustomer(Long id);

    Customer storeCustomer(Customer customer);

    void updateCustomer(Customer customer, Long id);

    Boolean deleteCustomer(Long id);

    // Redis Functions
    List<Customer> getAllRedisCustomer();

    Customer getRedisCustomer(Long id);

    // ElasticSearch Functions
    Iterable<ESCustomer> getAllESCustomer();

    ESCustomer getESCustomer(Long id);

}