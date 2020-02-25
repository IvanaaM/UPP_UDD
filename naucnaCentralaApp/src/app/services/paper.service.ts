import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaperService {
  
  
  constructor(private http: HttpClient) { }

  getAllPapers() {
    return this.http.get('http://localhost:8080/editor/getActiveInstances') as Observable<any>;
  }

  getPapersForIds(taskId) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get('http://localhost:8080/editor/getTask/' + taskId, {headers: headers}) as Observable<any>;
  }

  getPaperData(procIn) { 
    
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get('http://localhost:8080/editor/getPaperData/' + procIn, {headers: headers}) as Observable<any>;
  }

  post(taskId, o , type) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.post('http://localhost:8080/editor/post/' + taskId +'/' + type, o, {headers: headers}) as Observable<any>;
  }

  getPdfForPaper(taskId) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get('http://localhost:8080/magazine/getPdfCheck/' + taskId, {headers: headers}) as Observable<any>;
  }

  getPdfTaskCheck(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get('http://localhost:8080/magazine/get/' + instance, {headers: headers}) as Observable<any>;
  }

  getEditorComm(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.http.get('http://localhost:8080/user/getEditorCom/' + instance, {headers: headers}) as Observable<any>;
  }
  
}
