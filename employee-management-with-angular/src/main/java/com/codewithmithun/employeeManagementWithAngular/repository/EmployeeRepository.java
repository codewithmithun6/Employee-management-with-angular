package com.codewithmithun.employeeManagementWithAngular.repository;

import com.codewithmithun.employeeManagementWithAngular.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrMobileContainingIgnoreCase(
            String firstName, String lastName, String emailId, String Mobile, Pageable pageable);
}
