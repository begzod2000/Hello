package com.example.task.security;

import com.example.task.entity.User;
import com.example.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameAndActive(username, true);
        return user.map(value -> (UserDetails) value).orElseGet(() -> (UserDetails) ResponseEntity.status(HttpStatus.CONFLICT).body(username + "is not found"));
    }

    public UserDetails loadUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " +userId));
    }
}
