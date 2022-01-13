package com.example.task.service.impl;

import com.example.task.entity.Costs;
import com.example.task.modal.Response;
import com.example.task.modal.Status;
import com.example.task.payload.ReqBoolean;
import com.example.task.payload.ReqCosts;
import com.example.task.payload.ReqFilter;
import com.example.task.payload.ReqSalary;
import com.example.task.repository.CostsRepository;
import com.example.task.service.CostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class СostsServiceImpl implements CostsService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CostsRepository costsRepository;

    @Override
    public Response add(ReqCosts reqCosts) {
        Response response = new Response();
        Costs costs = new Costs();
        if (reqCosts.getName() != null) {
            Optional<Costs> byName = costsRepository.findByName(reqCosts.getName());
            if (!byName.isPresent()) {
                costs.setIncome(reqCosts.getIncome());
                costs.setName(reqCosts.getName());
                costs.setDate(new Date());
                costs.setSalary(reqCosts.getSalary());

                costsRepository.save(costs);
                response.setStatus(new Status(1, "Okey"));
            } else {
                response.setStatus(new Status(2, "Is present"));
            }
        }
        return response;
    }

    @Override
    public Response List(ReqBoolean reqBoolean) {
        Response response = new Response();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from costs where income = ? ", reqBoolean.getIncome());
        response.setData(list);
        response.setStatus(new Status(0, "Сosts List"));
        return response;
    }

    @Override
    public Response Filter(ReqFilter reqFilter) {
        Response response = new Response();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from costs where date BETWEEN  ? and ?", reqFilter.getDate(), reqFilter.getDate2());
        response.setData(list);
        response.setStatus(new Status(0, "Filter"));
        return response;
    }

    @Override
    public Response Salary(ReqSalary reqSalary) {
        Response response = new Response();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select sum(salary) from costs where date between ? and ?",reqSalary.getDate(),reqSalary.getDate2());
           response.setData(list);
           response.setStatus(new Status(0,"Sallary"));
        return response;
    }
}
