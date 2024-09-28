package com.codewithmithun.employeeManagementWithAngular.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {

    private Date timeStamp;
    private String message;
    private String details;
}
