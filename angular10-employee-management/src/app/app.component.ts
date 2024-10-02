import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Employee Management System';


  searchQuery: string = '';

  constructor(private router: Router) {}

  onSearch() {
    if (this.searchQuery.trim()) {
      // Navigate to the search results page
      this.router.navigate(['/employees'], { queryParams: { query: this.searchQuery } });
      console.log(this.searchQuery)
    }
  }

}
