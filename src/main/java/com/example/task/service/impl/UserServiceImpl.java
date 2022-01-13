package com.example.task.service.impl;


import com.example.task.entity.Role;
import com.example.task.entity.User;
import com.example.task.entity.enums.RoleName;
import com.example.task.modal.Response;
import com.example.task.modal.Status;
import com.example.task.payload.ReqSignUp;
import com.example.task.payload.UserData;
import com.example.task.repository.RoleRepository;
import com.example.task.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    public static final String ACCOUNT_SID = "ACf680a237a788f3a50845ba858f1c5b43";
    public static final String AUTH_TOKEN = "9ae18c44f224416031fa8c0ea7fe858c";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ObjectMapper objectMapper, JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;

        this.jdbcTemplate = jdbcTemplate;

    }



    public Response signUp(ReqSignUp reqSignUp) {
        Response response = new Response();
        Optional<User> exist = userRepository.findByUsername(reqSignUp.getUsername());
        Object data = null;
        if (exist.isPresent()) {
            response.setStatus(new Status(105,"Tizimda bunday User Mavjud"));
        } else {


            response.setStatus(new Status(0,"Success"));
        }
        response.setData(data);

        return response;
    }

    public UserData getUsers(User user) {
        List<RoleName> collect = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return new UserData(
                user.getId(),
                user.getUsername(),
                user.getActive(),
                user.getRoles()
        );
    }

    public Response listUsers(Boolean active){
        Response response=new Response();
        if (active==null) {
            List<UserData> userData = userRepository.findAll().stream().map(this::getUsers).collect(Collectors.toList());


//        List<Map<String, Object>> list = jdbcTemplate.queryForList("select au.id,au.username,au.phone,au.email,au.active,au.first_name,au.last_name,r.name as roles from app_user au left join user_role ur on au.id = ur.user_id left join role r on r.id = ur.role_id where r.name!='ROLE_TEACHER'");
            response.setData(userData);
            response.setStatus(new Status(0, "Success"));
        }else {
            List<Map<String, Object>> maps = jdbcTemplate.queryForList("select au.id,au.username,au.active from app_user au where au.active=?", active);
            for (int i = 0; i < maps.size(); i++) {
                Long id = (Long) maps.get(i).get("id");
                List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select r.id,r.name from role r left join user_role ur on ur.role_id=r.id where ur.user_id=?", id);
                maps.get(i).put("roles",maps1);
            }
            response.setData(maps);
            response.setStatus(new Status(0,"Success"));
        }
        return response;
    }

    public Response updateUser(ReqSignUp reqSignUp, Long id) {
        Response response = new Response();

        Optional<User> user1 = userRepository.findById(id);
        Optional<User> byUsernameAndActive = userRepository.findByUsername(reqSignUp.getUsername());
        if (user1.isPresent()) {
            if (!byUsernameAndActive.isPresent()||byUsernameAndActive.get().getUsername().equals(user1.get().getUsername())) {
                User user = user1.get();
                user.setUsername(reqSignUp.getUsername());
                user.setPassword(passwordEncoder.encode(reqSignUp.getPassword()));

                User save = userRepository.save(user);

                response.setData(save.getId());
                response.setStatus(new Status(0,"Success"));
            } else {
                response.setStatus(new Status(105,"Tizimda bunday User Mavjud"));
            }
        } else {
            response.setStatus(
                    new Status(101,"User topilmadi")
            );
        }
        return response;
    }

    public Response deleteUser(Long id){
        Response response=new Response();
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user=byId.get();
            user.setActive(false);
            userRepository.save(user);
            response.setStatus(new Status(0,"Success"));
        }else response.setStatus(new Status(101,"User topilmadi"));
        return response;
    }

    public Response activeUser(Long id){
        Response response=new Response();
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user=byId.get();
            user.setActive(!user.getActive());
            userRepository.save(user);
            response.setStatus(new Status(0,"Success"));
        }else response.setStatus(new Status(101,"User topilmadi"));
        return response;
    }


}
