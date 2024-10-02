package com.codewithmithun.employeeManagementWithAngular.model;

import com.codewithmithun.employeeManagementWithAngular.enumData.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "designation")
    private String designation;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    // Date of Birth in DD-MM-YYYY format
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Date of birth must be in DD-MM-YYYY format")
    private String dob;
    @Column(name = "years_of_experience")
    private String experience;

    public Employee(Long employeeId) {
    }
}
