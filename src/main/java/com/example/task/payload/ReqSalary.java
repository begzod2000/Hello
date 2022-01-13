package com.example.task.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqSalary {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

}
