package com.example.task.component;



import com.example.task.entity.User;
import com.example.task.repository.RoleRepository;
import com.example.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            userRepository.save(new User("salom",
                    passwordEncoder.encode("1234"),
                    roleRepository.findAll()));

//            userRepository.save(new User(
//                    "superAdmin",
//                    passwordEncoder.encode("root123"),
//                    "Super Administrator",
//                    roleRepository.findAll()));

//            userRepository.save(new User(
//                    "admin",
//                    passwordEncoder.encode("admin1"),
//                    "Administrator",
//                    roleRepository.findAllByName(RoleName.ROLE_ADMIN)));
//
//            userRepository.save(new User(
//                    "moderator",
//                    passwordEncoder.encode("moder123"),
//                    "Moderator",
//                    roleRepository.findAllByName(RoleName.ROLE_MODERATOR)));
//
//            userRepository.save(new User(
//                    "user",
//                    passwordEncoder.encode("123"),
//                    "Ismoil Miraliyev",
//                    roleRepository.findAllByName(RoleName.ROLE_USER)));

        }
    }
}
