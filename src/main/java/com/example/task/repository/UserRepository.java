package com.example.task.repository;


import com.example.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndActive(String username, boolean active);

    Optional<User> findByUsername(String username);
}
