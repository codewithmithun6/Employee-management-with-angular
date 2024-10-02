package com.codewithmithun.employeeManagementWithAngular.controller;
import com.codewithmithun.employeeManagementWithAngular.model.Address;
import com.codewithmithun.employeeManagementWithAngular.model.Employee;
import com.codewithmithun.employeeManagementWithAngular.repository.AddressRepository;
import com.codewithmithun.employeeManagementWithAngular.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/addresses/{employeeId}")
    public ResponseEntity<?>  saveOrUpdateAddress(@PathVariable("employeeId") Long employeeId, @RequestBody Address address) {
        // Fetch the employee by employeeId
        Employee employee = employeeRepository.findById(employeeId).get();

        if (employee == null) {
            // Handle the case where the student doesn't exist
            throw new RuntimeException("Student not found with id: " + employeeId);
        }

        // Check if an address already exists for the student
        Address existingAddress = addressRepository.getAddressByEmployeeId(employeeId);

        if (existingAddress != null) {
            // If an address already exists, update the existing address with the new data
            existingAddress.setState(address.getState());
            existingAddress.setCity(address.getCity());
            existingAddress.setStreet(address.getStreet());
            existingAddress.setPinCode(address.getPinCode());
            existingAddress.setCountry(address.getCountry());
            existingAddress.setFullAddress(address.getFullAddress());

            // Set the employee (though it might already be set)
            existingAddress.setEmployee(employee);

            // Save the updated address
            addressRepository.save(existingAddress);
            return ResponseEntity.ok(existingAddress);
        } else {
            // If no address exists, set the employee and create a new address
            address.setEmployee(employee);

            // Save the new address
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(address);
        }

    }


    // Endpoint to create or get an address based on student ID
    @GetMapping("/addresses/{employeeId}")
    public ResponseEntity<?> createAddress(@PathVariable Long employeeId) {

        // Check if an address already exists for the student
        Address existingAddress = addressRepository.getAddressByEmployeeId(employeeId);

        // If the address exists, return the address data
        if (existingAddress != null) {
            return ResponseEntity.ok(existingAddress);  // Return the existing address as JSON
        } else {
            // If no address exists, return a new address object for the employee
            Address address = new Address();
            // You could also include the employeeId in the response if needed.
//            address.setEmployee(new Employee(employeeId));

            return ResponseEntity.ok(address);  // Return a new Address object
        }
    }
}
