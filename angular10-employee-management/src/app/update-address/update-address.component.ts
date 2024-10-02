import { Component, OnInit } from '@angular/core';
import { Address } from '../address';
import { ActivatedRoute, Router } from '@angular/router';
import { AddressService } from '../address.service';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-update-address',
  templateUrl: './update-address.component.html',
  styleUrl: './update-address.component.css'  
})
export class UpdateAddressComponent implements OnInit{

  [x: string]: any;
  // address: any;

  id!: number;
  address!: Address;
  employee!: Employee;

  constructor(private route: ActivatedRoute,private router: Router,
    private addressService: AddressService,
    private employeeService:EmployeeService) { }

  ngOnInit(): void {
    this.address = new Address();
    this.employee = new Employee();
    this.id = this.route.snapshot.params['id'];

    this.addressService.getAddress(this.id)
      .subscribe((data: Address) => {
        console.log(data)
        this.address = data;
      }, (error: any) => console.log(error));    
   
  }

  updateAddress() {
    this.addressService.updateAddress(this.id, this.address)
      .subscribe((data: any) => {
        console.log(data);
        this.address = new Address();
        this.gotoList();
      }, (error: any) => console.log(error));
  }
  
  
  
  onSubmit() {
    this.updateAddress();
    this.gotoList();    
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
