import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit{

  employees!: Observable<Employee[]>;

  constructor(private employeeService:EmployeeService,
    private router: Router){}

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData(){
    this.employees = this.employeeService.getEmployeesList();
    
  }

 

  deleteEmployee(id:number){
    const confirmation = confirm('Are you sure you want to delete this employee?');
    if (confirmation) {      
    this.employeeService.deleteEmployee(id)
    .subscribe(
      data => {
        console.log(data);
        this.reloadData();
      },
      error => console.log(error));
    }
}

updateEmployee(id: number){
  const confirmation = confirm('Are you sure you want to update this employee?');
  if (confirmation) { 
  this.router.navigate(['update', id]);
  }
}

employeeDetails(id: number){
  const confirmation = confirm('Are you sure you want to view this employee?');
  if (confirmation) { 
  this.router.navigate(['details', id]);
  }
}  
}
