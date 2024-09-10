import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Department } from '../models/department.types';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  constructor(private _httpClient: HttpClient) {}

  add(department: Department): Observable<any> {
    return this._httpClient.post(
      `${environment.apiBaseUri}/department/save`,
      department
    );
  }

  edit(id: number, name: string): Observable<any> {
    return this._httpClient.put(`${environment.apiBaseUri}/department/${id}`, {
      name,
    });
  }

  get(id: number): Observable<Department> {
    return this._httpClient.get<Department>(
      `${environment.apiBaseUri}/department/${id}`
    );
  }

  getAll(): Observable<Department[]> {
    return this._httpClient.get<Department[]>(
      `${environment.apiBaseUri}/department/allDepartment`
    );
  }

  delete(id: number): Observable<any> {
    return this._httpClient.delete(
      `${environment.apiBaseUri}/department/${id}`
    );
  }
}
