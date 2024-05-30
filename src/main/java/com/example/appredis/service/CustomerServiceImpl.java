package com.example.appredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.appredis.model.Customer;
import com.example.appredis.model.ESCustomer;
import com.example.appredis.repository.CustomerESRepository;
import com.example.appredis.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerServices {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerESRepository customerESRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY = "USER";

    @Override
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            return new ArrayList<Customer>();
        }
        return customerList;
    }

    @Override
    public Customer getCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new Customer();
        }
        Customer customer = optionalCustomer.get();
        return customer;
    }

    @Override
    public Customer storeCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByName(customer.getName());
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer with the same name already exists");
        }
        Customer newCustomer = customerRepository.saveAndFlush(customer);
        // redis
        redisTemplate.opsForHash().put(KEY, newCustomer.getId(), newCustomer);
        // elasticsearch
        ESCustomer esCustomer = new ESCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getAge());
        customerESRepository.save(esCustomer);
        return newCustomer;
    }

    @Override
    public void updateCustomer(Customer customer, Long id) {
        try {
            Customer newCustomer = customerRepository.save(customer);
            // redis
            redisTemplate.opsForHash().put(KEY, id, newCustomer);
            // elasticsearch
            ESCustomer esCustomer = new ESCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getAge());
            customerESRepository.save(esCustomer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
            // redis
            redisTemplate.opsForHash().delete(KEY, id);
            // elasticsearch
            customerESRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Redis
    @Override
    public List<Customer> getAllRedisCustomer() {
        List<Customer> customerList = redisTemplate.opsForHash().values(KEY);
        if (customerList.isEmpty()) {
            return new ArrayList<Customer>();
        }
        return customerList;
    }

    @Override
    public Customer getRedisCustomer(Long id) {
        Customer customer = (Customer) redisTemplate.opsForHash().get(KEY, id);
        return customer;
    }

    // ElasticSearch
    @Override
    public Iterable<ESCustomer> getAllESCustomer() {
        return customerESRepository.findAll();
    }

    @Override
    public ESCustomer getESCustomer(Long id) {
        Optional<ESCustomer> optionalCustomer = customerESRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new ESCustomer();
        }
        ESCustomer customer = optionalCustomer.get();
        return customer;
    }

}
