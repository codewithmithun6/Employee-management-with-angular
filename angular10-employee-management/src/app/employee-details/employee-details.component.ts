import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee.service';
import id from '@angular/common/locales/id';
import { Address } from '../address';
import { AddressService } from '../address.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrl: './employee-details.component.css'
})
export class EmployeeDetailsComponent implements OnInit {
[x: string]: any;

  id!: number;
  employee!: Employee;
  address!: Address;

  constructor(private route: ActivatedRoute,private router: Router,
    private employeeService: EmployeeService,
    private addressService: AddressService) { }

    
  ngOnInit(): void {
    this.employee = new Employee();
    this.displayEmployeeDetails();    
    this.displayAddressDetails();    
  }

  displayEmployeeDetails(){
    this.id = this.route.snapshot.params['id'];
    this.employeeService.getEmployee(this.id)
      .subscribe((data: Employee) => {
        console.log(data)
        this.employee = data;
      }, (error: any) => console.log(error));

  }

  displayAddressDetails(){
    this.id = this.route.snapshot.params['id'];
    this.addressService.getAddress(this.id)
      .subscribe((data: Address) => {
        console.log(data)        
          this.address = data;        
      }, (error: any) => console.log(error));
    
  }

  list(){
    this.router.navigate(['employees']);
  }

  // updateEmployee(id:number){
  //   const confirmation = confirm('Are you sure you want to update the address?');
  //   if (confirmation) { 
  //     this.router.navigate(['update-address',id]);
  //   }   

  // }



updateEmployee(id: number) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You are about to update the employee address!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, update it!',
    cancelButtonText: 'No, cancel'
  }).then((result) => {
    if (result.isConfirmed) {
      this.router.navigate(['update-address', id]);
      // Swal.fire(
      //   'Updated!',
      //   'The employee address has been updated.',
      //   'success'
      // );
    }
  });
}

}  
 


