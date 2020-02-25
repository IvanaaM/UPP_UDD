import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Login } from '../modelDTO/login';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  log: any;
  roles = [];
  out: boolean = false;
  admin: boolean = false;
  urednik: boolean = false;
  recenzent: boolean = false;

  constructor(protected router: Router, private userService: UserService) { 

  }

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('logged'));
    this.checkLogged();
  }
  checkLogged(){
   
  if (this.log != null){

    this.roles = JSON.parse(localStorage.getItem('roles'));
 

     this.roles.forEach(element => {
       if(element.toString() == 'E'){
        this.urednik = true;
       }
       
       if (element.toString() == 'A'){
        this.admin = true;
       }
        if (element.toString() == 'R'){
          this.recenzent = true;
       }
     });
     
      
  
     }
  }
  
  

  registracija(){
    this.router.navigateByUrl('/register/user');
  }

  admins(){
    this.router.navigateByUrl('/admin');
  }

  editors(){
    this.router.navigateByUrl('/editor');
  }

  login(){
    this.router.navigateByUrl('/login');
  }

  reviewers(){
    this.router.navigateByUrl('/reviewers');
  }

  logout(){
    localStorage.removeItem('logged');
    localStorage.removeItem('roles');
    this.out = true;
    this.urednik = false;
    this.admin = false;
    this.recenzent = false;
    window.location.reload();
    //this.router.navigateByUrl('');
  }

}
