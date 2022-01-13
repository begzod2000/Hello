package com.example.task.service;

import com.example.task.modal.Response;
import com.example.task.payload.ReqBoolean;
import com.example.task.payload.ReqCosts;
import com.example.task.payload.ReqFilter;
import com.example.task.payload.ReqSalary;

public interface CostsService {
    Response add(ReqCosts reqCosts);
    Response List(ReqBoolean reqBoolean);
    Response Filter(ReqFilter reqFilter);
    Response Salary(ReqSalary reqSalary);

}
