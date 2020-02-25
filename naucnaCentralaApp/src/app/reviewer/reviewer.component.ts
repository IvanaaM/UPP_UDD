import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reviewer',
  templateUrl: './reviewer.component.html',
  styleUrls: ['./reviewer.component.css']
})
export class ReviewerComponent implements OnInit {

  constructor(protected router: Router) { }

  ngOnInit() {
  }

  goToReview(){
    this.router.navigateByUrl('review');
  }

}
