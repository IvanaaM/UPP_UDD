import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  
  constructor(private httpClient: HttpClient, private http : Http) { }

  startProcess(){
    return this.httpClient.get('http://localhost:8080/user/startProcessReg') as Observable<any>
  }

  startProcessMagazine(){
    return this.httpClient.get('http://localhost:8080/editor/createMagazine') as Observable<any>
  }

  getTask(procIn) {
    return this.httpClient.get('http://localhost:8080/user/getTask/' + procIn) as Observable<any>
  }
}
