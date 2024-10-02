package com.codewithmithun.employeeManagementWithAngular.repository;


import com.codewithmithun.employeeManagementWithAngular.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Address getAddressByEmployeeId(Long employeeId);
}
