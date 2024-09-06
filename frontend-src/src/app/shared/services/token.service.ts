import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor() {}

  isAuth = (): boolean => this.getToken() !== null;

  getToken = () => localStorage.getItem('token');
  setToken = (value: string) => localStorage.setItem('token', value);
  removeToken = () => localStorage.removeItem('token');
}
