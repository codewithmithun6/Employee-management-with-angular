import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private baseUrl = 'http://localhost:8230/springboot-crud-rest/api/v1/addresses';

  constructor(private http: HttpClient) { }

  getAddress(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  updateAddress(id: number, value: any): Observable<Object> {
    return this.http.post(`${this.baseUrl}/${id}`, value);
  }
}
