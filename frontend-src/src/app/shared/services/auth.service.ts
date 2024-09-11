import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import * as CryptoJS from 'crypto-js';



@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private _httpClient: HttpClient) {}

  login(email: string, password: string) {
    const hashedPassword = CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex);
  console.log(hashedPassword);
  debugger
    return this._httpClient.post(`${environment.apiBaseUri}/api/auth/login`, {
      email,
      password: hashedPassword,
    });
  }
  
}
