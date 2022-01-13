package com.example.task.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqSignUp {
    @NotBlank
    private String username;

    @NotBlank
    private String password;



}