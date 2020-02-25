import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json', 'logged' : JSON.parse(localStorage.getItem('logged'))}),
};

@Injectable({
  providedIn: 'root'
})
export class EditorService {
  
  constructor(private httpClient: HttpClient, private http : Http) { }

  startProcess(){
    return this.httpClient.get('http://localhost:8080/editor/createMagazine') as Observable<any>;
  }

  registerMagazine(o, taskId, type){
    return this.httpClient.post('http://localhost:8080/editor/post/'+ taskId + "/" + type, o) as Observable<any>;
  }

  getTask(procIn){
    return this.httpClient.get('http://localhost:8080/editor/getTask/' + procIn, httpOptions) as Observable<any>;
  }

  postNO(o, taskId, type){
    return this.httpClient.post('http://localhost:8080/editor/post/'+ taskId + "/" + type, o) as Observable<any>;
  }

  getReviewers(procIn){
    const type='reviewers';
    return this.httpClient.get('http://localhost:8080/editor/get/' + procIn) as Observable<any>;
  }

  setReviewers(o, taskId, type){
    return this.httpClient.post('http://localhost:8080/editor/post/'+ taskId + "/" + type, o) as Observable<any>;
  }

  setEditors(o, taskId, type){
    return this.httpClient.post('http://localhost:8080/editor/post/'+ taskId + "/" + type, o) as Observable<any>;
  }
  
  getMagazine(procIn){
    return this.httpClient.get("http://localhost:8080/admin/getMagazine/"+ procIn, httpOptions) as Observable<any>;
  }

  getNext(procIn){
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/get/' + procIn, {headers: headers}) as Observable<any>;
  }

  post(o, taskId, type){
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/editor/post/'+ taskId + "/" + type, o, {headers: headers}) as Observable<any>;
  }

  setDate(procIn) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/checkHasTasks/' + procIn, {headers: headers}) as Observable<any>
  }

  getCoauthors(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/getCoauthors/' + instance, {headers: headers}) as Observable<any>
  }

}
