import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class QrService {
  constructor(private _httpClient: HttpClient) {}

  send(id: string): Observable<any> {
    return this._httpClient.post<any>(`${environment.apiBaseUri}/api/qr/scan`, {
      code: id,
    });
  }
}
