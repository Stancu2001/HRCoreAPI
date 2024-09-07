import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Department } from '../models/department.types';
import { Employee } from '../models/employee.types';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  constructor(private _httpClient: HttpClient) {}

  add(data: any): Observable<any> {
    const formData: FormData = new FormData();

    formData.append(
      'Address',
      new Blob([JSON.stringify(data.address)], { type: 'application/json' })
    );
    formData.append(
      'Employee',
      new Blob([JSON.stringify(data.employee)], { type: 'application/json' })
    );
    formData.append(
      'idfunctie',
      new Blob([JSON.stringify(data.function)], { type: 'application/json' })
    );

    return this._httpClient.post(
      `${environment.apiBaseUri}/employee/save`,
      formData
    );
  }

  delete(id: number): Observable<any> {
    return this._httpClient.delete(`${environment.apiBaseUri}/employee/${id}`);
  }

  setSalary(employee: Employee, salary: number): Observable<any> {
    return this._httpClient.post(
      `${environment.apiBaseUri}/salary/add/${employee.idEmployee}`,
      { salary: salary }
    );
  }

  modifySalary(employee: Employee, salary: number): Observable<any> {
    return this._httpClient.put(
      `${environment.apiBaseUri}/salary/modify/${employee.idEmployee}`,
      { percent: salary }
    );
  }
}
