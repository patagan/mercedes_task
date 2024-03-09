import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  login(userLogin:User): Observable<any> {
    return this.http.post<any>('http://localhost:8080/login', userLogin);
  }

  register(userLogin:User): Observable<any> {
    return this.http.post<any>('http://localhost:8080/register', userLogin);
  }

  saveToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  clearToken() {
    localStorage.removeItem('jwtToken');
  }
}