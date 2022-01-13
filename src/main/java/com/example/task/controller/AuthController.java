package com.example.task.controller;


import com.example.task.entity.User;
import com.example.task.modal.Response;
import com.example.task.modal.Status;
import com.example.task.payload.ReqSignIn;
import com.example.task.payload.ReqSignUp;
import com.example.task.repository.UserRepository;
import com.example.task.security.JwtTokenProvider;
import com.example.task.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;



    @Value("${app.jwtExpirationInMs}")
    private long accessTokenDate;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ObjectMapper objectMapper, UserServiceImpl userService, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqSignIn reqSignIn){
        ObjectNode data = objectMapper.createObjectNode();
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(reqSignIn.getUsername(), true);
        Status status;

        if (optionalUser.isPresent()){

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(reqSignIn.getUsername(), reqSignIn.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.refreshToken(authentication);
            data.put("accessToken", jwt);
            data.put("refreshToken", refreshToken);
            data.put("tokenType","Bearer ");
            data.put("expiryDate",accessTokenDate);
            status=new Status(0,"Success");
        }
        else {
            status=new Status(101,"User topilmadi");
        }

        return ResponseEntity.ok(new Response(data,status));
    }

    @PostMapping("/create")
    public HttpEntity<?> signUp(@RequestBody ReqSignUp reqSignUp){

        return ResponseEntity.ok(userService.signUp(reqSignUp));
    }


    @PostMapping("/update/{id}")
    public HttpEntity<?> updateUser(@RequestBody ReqSignUp reqSignUp, @PathVariable Long id){
        Response response = userService.updateUser(reqSignUp, id);
        return ResponseEntity.ok(response);
    }

}

