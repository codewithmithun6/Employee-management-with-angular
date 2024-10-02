import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit{

  employees!: Observable<Employee[]>; 
  // employees: Employee[] = []; 
  currentPage: number = 0;
  totalPages: number = 0;
  totalItems: number = 0;
  pageSize: number = 5;  // Default page size
  keyword: string = '';  // Default search keyword 

  constructor(private employeeService:EmployeeService,
    private router: Router){}

  ngOnInit(): void {
    
    this.reloadData();
    // this.getEmployees();
  }

  reloadData(){    
    // this.employees = this.employeeService.getEmployeesList();
    this.employees = this.employeeService.getEmployees(this.currentPage,this.pageSize,this.keyword);
    
  }

 

//   deleteEmployee(id:number){
//     const confirmation = confirm('Are you sure you want to delete this employee?');
//     if (confirmation) {      
//     this.employeeService.deleteEmployee(id)
//     .subscribe(
//       data => {
//         console.log(data);
//         this.reloadData();
//       },
//       error => console.log(error));
//     }
// }

deleteEmployee(id: number) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You want to delete this employee?!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!',
    cancelButtonText: 'No, cancel'
  }).then((result) => {
    if (result.isConfirmed) {           
        this.employeeService.deleteEmployee(id)
        .subscribe(
          data => {
            console.log(data);
            this.reloadData();
          },
          error => console.log(error));
        }    
    });
}

// updateEmployee(id: number){
//   const confirmation = confirm('Are you sure you want to update this employee?');
//   if (confirmation) { 
//   this.router.navigate(['update', id]);
//   }
// }

updateEmployee(id: number) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You want to update this employee?!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, update it!',
    cancelButtonText: 'No, cancel'
  }).then((result) => {
    if (result.isConfirmed) {
      this.router.navigate(['update', id]);    
    }
  });
}

// employeeDetails(id: number){
//   const confirmation = confirm('Are you sure you want to view this employee?');
//   if (confirmation) { 
//   this.router.navigate(['details', id]);
//   }
// } 

employeeDetails(id: number) {
  Swal.fire({
    title: 'Are you sure?',
    text: "You want to view this employee?!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, View it!',
    cancelButtonText: 'No, cancel'
  }).then((result) => {
    if (result.isConfirmed) {
      this.router.navigate(['details', id]);    
    }
  });
}


createEmployee(){  
  this.router.navigate(['add']);  
}


// ---search and paginations---

getEmployees(): void {
  this.employeeService.getEmployees(this.currentPage, this.pageSize, this.keyword).subscribe(data => {
    this.employees = data.employees;
    this.currentPage = data.currentPage;
    this.totalPages = data.totalPages;
    this.totalItems = data.totalItems;
  },
  error => {
    console.error('Error fetching data', error);
  }

);
}

 // Handle page change
 onPageChange(newPage: number): void {
  if (newPage >= 0 && newPage < this.totalPages) {
    this.currentPage = newPage;
    this.getEmployees();
    
  }
  
}

 // Handle search
 onSearch(): void {
  this.currentPage = 0;  // Reset to the first page on search
  this.getEmployees();
}

}
