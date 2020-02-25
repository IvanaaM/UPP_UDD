import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';
import { LoginDTO } from 'src/app/modelDTO/loginDTO';
import { Login } from 'src/app/modelDTO/login';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
 
 
  constructor(private httpClient: HttpClient) { }

  registerUser(user, taskId, type) {
    return this.httpClient.post("http://localhost:8080/user/post/".concat(taskId) + "/" + type, user, httpOptions) as Observable<any>;
  }

  postNO(o, taskId: string, type: string) { 
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post("http://localhost:8080/user/post/" + taskId +"/" + type, o, {headers: headers}) as Observable<any>;
  }

  login(log){
    const body = JSON.stringify(log);
    return this.httpClient.post("http://localhost:8080/user/login", body,  httpOptions) as Observable<any>;
  }

  getLoggedUser(){
    let log = new Login();
    log = JSON.parse(localStorage.getItem('logged'));
    return log;
  } 
  
  checkRoles() {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/user/getRoles', {headers: headers}) as Observable<any>
  }
  checkHasTasks(procIn) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/checkHasTasks/' + procIn, {headers: headers}) as Observable<any>
  }

  getTaskForUser(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/user/getTask/' + instance, {headers: headers}) as Observable<any>
  }

  shouldPay(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/user/shouldPay/' + instance, {headers: headers}) as Observable<any>
  }

  postCoauthor(o, taskId, type) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post("http://localhost:8080/user/postCoauthor/" + taskId +"/" + type, o,  {headers: headers}) as Observable<any>;
  }

  getComments(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get("http://localhost:8080/reviewer/getReviews/"  + instance,  {headers: headers}) as Observable<any>;
  }
 
 
  logout(){
    
  }

}
