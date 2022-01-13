package com.example.task.payload;



import com.example.task.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Long id;
    private String username;
    private boolean active;
    private List<Role> roles;
}
