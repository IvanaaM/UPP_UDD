import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
 
 
  constructor(private httpClient: HttpClient) { }

  getTask(procIn) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/editor/get/' + procIn, {headers: headers}) as Observable<any>
  }

  post(o, taskId, type) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/editor/post/' + taskId +"/"+type, o, {headers: headers}) as Observable<any>
  }

  getTaskForReview(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/reviewer/getTask/'+ instance, {headers: headers}) as Observable<any>
  }

  postReview(o, taskId) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.post('http://localhost:8080/reviewer/post/'+ taskId, o, {headers: headers}) as Observable<any>
  }

  getAllReviews(instance) {
    const token = localStorage.getItem('logged');
    const headers = new HttpHeaders({'Content-Type': 'application/json', 'token': token});
    return this.httpClient.get('http://localhost:8080/reviewer/getReviews/'+ instance, {headers: headers}) as Observable<any>
  }

}
