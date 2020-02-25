import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {
  
  constructor(private httpClient: HttpClient, private http : Http) { }

  getMagazines(){
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/magazine/getAll', {headers: headers}) as Observable<any>
  }

  postChooseMagazine(o, taskId, type) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/user/post/' +taskId+"/"+type , o, {headers: headers}) as Observable<any>
  }

  getDataForPaper(procIn) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/get/' +procIn, {headers: headers}) as Observable<any>
  }

  postMagazineData(o, taskId, type) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/magazine/post/' + taskId +"/" + type, o, {headers: headers}) as Observable<any>
  }

  postPdfBytes(pd, urlPdf) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/magazine/postForPdf/' + pd, urlPdf, {headers: headers}) as Observable<any>
  }
  
}
