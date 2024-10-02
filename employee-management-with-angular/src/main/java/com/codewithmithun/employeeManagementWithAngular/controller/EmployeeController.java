package com.codewithmithun.employeeManagementWithAngular.controller;

import com.codewithmithun.employeeManagementWithAngular.exception.ResourceNotFoundException;
import com.codewithmithun.employeeManagementWithAngular.model.Address;
import com.codewithmithun.employeeManagementWithAngular.model.Employee;
import com.codewithmithun.employeeManagementWithAngular.repository.AddressRepository;
import com.codewithmithun.employeeManagementWithAngular.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found for this id:: " + employeeId));
        return ResponseEntity.ok().body(employee);

    }

    @PostMapping("/employees")
    public Employee createEmployee(@Validated @RequestBody Employee employee){

        return employeeRepository.save(employee);

    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found for this id:: " + employeeId));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setMobile(employeeDetails.getMobile());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setGender(employeeDetails.getGender());
        employee.setDob(employeeDetails.getDob());
        employee.setExperience(employeeDetails.getExperience());
        final Employee updatedEmployee = employeeRepository.save(employee);

        return ResponseEntity.ok().body(updatedEmployee);
    }

//    @DeleteMapping("/employees/{id}")
//    public Map<String, Boolean>  deleteEmployee(@PathVariable("id") Long employeeId) throws ResourceNotFoundException {
//
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found for this id:: " + employeeId));
//
//        employeeRepository.delete(employee);
//
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("Deleted",Boolean.TRUE);
//        return response;
//    }


    @DeleteMapping("employees/{id}")
    public ResponseEntity<String> deleteEmployeeOrAddress(@PathVariable Long id) {
        LOGGER.info("Delete button triggered for employee ID: " + id);

        // Fetch the address associated with the employee, might be null
        Address employeeAddress = addressRepository.getAddressByEmployeeId(id);

        if (employeeAddress != null && employeeAddress.getEmployee() != null) {
            LOGGER.info("Employee Address found: " + employeeAddress.getEmployee().getId());

            // Safely compare ids and handle the deletion
            if (id != null && id.equals(employeeAddress.getEmployee().getId())) {
                Long addressId = employeeAddress.getId();

                // Delete address if it exists
                addressRepository.deleteById(addressId);
                employeeRepository.deleteById(id);
            } else {
                // If the ids don't match, just delete the employee
                employeeRepository.deleteById(id);
            }
        } else {
            // If employeeAddress or employee is null, delete the student by id
            LOGGER.info("Address or employee is null, only deleting employee");
            employeeRepository.deleteById(id);
        }
        return ResponseEntity.ok("employee and associated address (if any) deleted successfully");
    }


//    ----search employee------

    @GetMapping("/employees/new")
    public ResponseEntity<Map<String, Object>> listEmployee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage;

        // If search keyword is present, perform search
        if (keyword != null && !keyword.trim().isEmpty()) {
            employeePage = searchEmployees(keyword, pageable);
            System.out.println("if: "+employeePage);
        } else {
            employeePage = getAllEmployees(pageable);
            System.out.println("else: "+employeePage);
        }

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("employees", employeePage.getContent());           // Current page data
        response.put("currentPage", employeePage.getNumber());         // Current page number
        response.put("totalItems", employeePage.getTotalElements());   // Total students
        response.put("totalPages", employeePage.getTotalPages());   // Total pages
        System.out.println(employeePage.getContent());
        System.out.println(employeePage.getNumber());
        System.out.println(employeePage.getTotalElements());
        System.out.println(employeePage.getTotalPages());

        System.out.println(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Method to fetch all students with pagination
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    // Method to search students by keyword with pagination
    public Page<Employee> searchEmployees(String keyword, Pageable pageable) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrMobileContainingIgnoreCase(keyword, keyword, keyword,keyword, pageable);
    }

}
