import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Function } from '../models/function.types';

@Injectable({
  providedIn: 'root',
})
export class FunctionService {
  constructor(private _httpClient: HttpClient) {}

  add(departmentId: number, func: Function): Observable<any> {
    return this._httpClient.post(
      `${environment.apiBaseUri}/function/${departmentId}/save`,
      func
    );
  }

  edit(func: any): Observable<any> {
    return this._httpClient.put(
      `${environment.apiBaseUri}/function/${func.functionId}`,
      {
        name: func.name,
      }
    );
  }

  delete(id: number): Observable<any> {
    return this._httpClient.delete(
      `${environment.apiBaseUri}/function/${id}`
    );
  }
}
