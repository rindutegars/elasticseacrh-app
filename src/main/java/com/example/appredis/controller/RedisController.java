package com.example.appredis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.appredis.model.Customer;
import com.example.appredis.service.CustomerServices;

@RestController
@RequestMapping("/redisApi")
public class RedisController {

    @Autowired
    CustomerServices customerServices;

    @GetMapping("/getAllRedisCustomer")
    @ResponseBody
    public ResponseEntity<?> getAllRedisCustomerData() {
        List<Customer> customerList = customerServices.getAllRedisCustomer();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getRedisCustomer/{id}")
    @ResponseBody
    public ResponseEntity<?> getRedisCustomerData(@PathVariable Long id) {
        Customer customer = customerServices.getRedisCustomer(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer with ID " + id + " loaded successfully");
        response.put("data", customer);
        return ResponseEntity.ok(response);
    }
}
