package com.example.appredis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.example.appredis.service.CustomerServices;
import com.example.appredis.model.ESCustomer;

@RestController
@RequestMapping("/esApi")
public class ElasticSearchController {

    @Autowired
    CustomerServices customerServices;

    @GetMapping("/getAllESCustomer")
    @ResponseBody
    public ResponseEntity<?> getAllESCustomerData() {
        Iterable<ESCustomer> customerList = customerServices.getAllESCustomer();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getESCustomer/{id}")
    @ResponseBody
    public ResponseEntity<?> getESCustomerData(@PathVariable Long id) {
        ESCustomer customer = customerServices.getESCustomer(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer with ID " + id + " loaded successfully");
        response.put("data", customer);
        return ResponseEntity.ok(response);
    }
}
