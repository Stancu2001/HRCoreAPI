import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Function } from '../models/function.types';
import { Payroll } from '../models/employee.types';

@Injectable({
  providedIn: 'root',
})
export class PayrollService {
  constructor(private _httpClient: HttpClient) {}

  calculate(employeeId?: number): Observable<any> {
    if(!employeeId) {
      return this._httpClient.get(
        `${environment.apiBaseUri}/payroll/calculate-net`
      );
    }

    return this._httpClient.get(
      `${environment.apiBaseUri}/payroll/calculate-net/${employeeId}`
    );
  }
}
