package com.example.task.controller;

import com.example.task.modal.Response;
import com.example.task.payload.ReqBoolean;
import com.example.task.payload.ReqCosts;
import com.example.task.payload.ReqFilter;
import com.example.task.payload.ReqSalary;
import com.example.task.service.CostsService;
import com.example.task.service.impl.СostsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/costs")
public class CostsController {
    @Autowired
    СostsServiceImpl costsService;



      @PostMapping("/add")
    private HttpEntity<?> add(@RequestBody ReqCosts reqCosts){
          Response add = costsService.add(reqCosts);
          return ResponseEntity.ok(add);
      }
    @GetMapping("/list")
    private HttpEntity<?> list(@RequestBody ReqBoolean reqBoolean){
     Response list = costsService.List(reqBoolean);
      return ResponseEntity.ok(list);
    }
    @PostMapping("/filter")
    private HttpEntity<?> filter(@RequestBody ReqFilter reqFilter){
          Response filter = costsService.Filter(reqFilter);
          return ResponseEntity.ok(filter);
    }
    @PostMapping("/salary")
    private HttpEntity<?> salary(@RequestBody ReqSalary reqSalary){
          Response salary = costsService.Salary(reqSalary);
          return ResponseEntity.ok(salary);
    }

}
