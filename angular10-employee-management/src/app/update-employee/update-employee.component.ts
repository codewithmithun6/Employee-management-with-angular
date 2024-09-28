import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from '../employee.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrl: './update-employee.component.css'
})
export class UpdateEmployeeComponent implements OnInit{
[x: string]: any;

  id!: number;
  employee!: Employee;

  constructor(private route: ActivatedRoute,private router: Router,
    private employeeService: EmployeeService) { }
    
  ngOnInit(): void {
    this.employee = new Employee();

    this.id = this.route.snapshot.params['id'];
    
    this.employeeService.getEmployee(this.id)
      .subscribe((data: Employee) => {
        console.log(data)
        this.employee = data;
      }, (error: any) => console.log(error));  
  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.id, this.employee)
      .subscribe((data: any) => {
        console.log(data);
        this.employee = new Employee();
        this.gotoList();
      }, (error: any) => console.log(error));
  }

  onSubmit() {
    this.updateEmployee();
    // this.gotoList();    
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
