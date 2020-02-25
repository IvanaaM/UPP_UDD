import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class AdminServiceService {

  constructor(private httpClient: HttpClient) { }

  getRec(){
    const a = JSON.parse(localStorage.getItem('instance'));
    return this.httpClient.get("http://localhost:8080/admin/getRec/"+ a, httpOptions) as Observable<any>;
  }

  getUser(){
  
    const a = JSON.parse(localStorage.getItem('instance'));
    return this.httpClient.get("http://localhost:8080/admin/getUser/"+ a, httpOptions) as Observable<String>;
  }

  saveRec(o, taskId){
    const a = JSON.parse(localStorage.getItem('instance'));
   // o = JSON.stringify(localStorage.getItem('instance'));
    return this.httpClient.post("http://localhost:8080/admin/saveRec/" + taskId, o, httpOptions) as Observable<any>;
  }



}
