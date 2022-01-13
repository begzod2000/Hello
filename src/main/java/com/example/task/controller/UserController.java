package com.example.task.controller;



import com.example.task.config.SecurityConfig;
import com.example.task.entity.User;
import com.example.task.modal.Response;
import com.example.task.modal.Status;
import com.example.task.repository.RoleRepository;
import com.example.task.repository.UserRepository;
import com.example.task.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile/")
public class UserController {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UserController(UserRepository userRepository, SecurityConfig securityConfig, ObjectMapper objectMapper, UserServiceImpl userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.objectMapper = objectMapper;
        this.userService = userService;

        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @GetMapping("data")
    public HttpEntity<?> getUserData(){
       Response response = new Response();
        Status status = null;
        ObjectNode data = objectMapper.createObjectNode();
        User currentUser = securityConfig.getCurrentUser();
        data.put("id", currentUser.getId());



        data.put("username",currentUser.getUsername());

        data.putPOJO("role", currentUser.getRoles());


        response.setStatus(new Status(0,"Success"));
        response.setData(data);
        return ResponseEntity.ok(response);
    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ADMIN')")
//    @GetMapping("list")
//    public HttpEntity<?> getUsersList(){
//        Response response = new Response();
//        Message status;
//        ObjectNode data = objectMapper.createObjectNode();
//        List<UserData> userData = userRepository.findAll().stream().map(userService::getUsers).collect(Collectors.toList());
//        data.putPOJO("content", userData);
//        status =messageRepository.findByCode(0) ;
//        response.setMessage(status);
//        response.setData(data);
//        return ResponseEntity.ok(response);
//    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping("list")
    public HttpEntity<?> getUsersList(@RequestParam(required = false)Boolean active){
        return ResponseEntity.ok(userService.listUsers(active));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public HttpEntity<?> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @PostMapping("active/{id}")
    public HttpEntity<?> activeUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.activeUser(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    @GetMapping("role")
    public HttpEntity<?> getRoles(){
        Response response =new Response();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id, name from role");
        response.setData(list);
        response.setStatus(new Status(0,"Success"));
        return ResponseEntity.ok(response);
    }


}