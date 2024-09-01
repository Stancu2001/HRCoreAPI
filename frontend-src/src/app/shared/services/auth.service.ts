import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private _httpClient: HttpClient) {}

  login(email: string, password: string) {
    return this._httpClient.post(`${environment.apiBaseUri}/api/auth/login`, {
      email,
      password,
    });
  }
}
