import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from '../modelDTO/login';
import { element } from '@angular/core/src/render3';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  log= [];
  show: boolean = false;
  list: [];
  hasTasks: boolean = true;
  noboody: any;
  urednik: boolean = false;
  recenzent: boolean = false;
  admin: boolean = false;
  data: any;

  constructor(protected router: Router, private userService: UserService) {

   }

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('roles'));

    if (this.log != null){
      this.noboody = false;
    //  console.log(this.log);
      this.log.forEach(element => {

        if(element.toString() == 'U'){
          this.show = true;
          this.noboody = true;
          
        }

        if(element.toString() == 'E'){
          this.urednik = true;
        }

        if(element.toString() == 'R'){
          this.recenzent = true;
        }
       
        if(element.toString() == 'A'){
          this.admin = true;
        }
      });
     
     } else { 
      
     }

  }

report(){
    this.router.navigateByUrl('/chooseMagazine');
}

// proveriti da li postoji korigovanje 
changePaper(){

  this.router.navigateByUrl('profil');

}

ured(){
  this.router.navigateByUrl('editor');
}

rec(){
  this.router.navigateByUrl('reviewers');
}

admin2(){
  this.router.navigateByUrl('admin');
}

}
