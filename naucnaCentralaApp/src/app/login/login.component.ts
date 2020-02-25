import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { LoginDTO } from '../modelDTO/loginDTO';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public nameuser: String;
  public pass: String;
  public show: Boolean = false;
  public form: FormGroup;
  public username1: AbstractControl;
  public password1: AbstractControl;

  constructor(private fb: FormBuilder, private userService: UserService, protected router: Router) {
    this.form = this.fb.group({
      'username1': ['', Validators.compose([Validators.required])],
      'password1': ['', Validators.compose([Validators.required])],
    });
    this.username1 = this.form.controls['username1'];
    this.password1 = this.form.controls['password1'];

  }
   

  ngOnInit() {
  }

  login(){

    let log = new LoginDTO();
    log.password = this.password1.value;
    log.username = this.username1.value;
    
    this.userService.login(log).subscribe( user => {
      console.log(user);

      if (user.accessToken == null) {
        alert('Ne postoji korisnik sa unetim kredencijalima!');
        return;
      }

        localStorage.setItem('logged', JSON.stringify(user.accessToken));

        this.userService.checkRoles().subscribe( r => {

          console.log(r);
          localStorage.setItem('roles', JSON.stringify(r));
    
          this.router.navigate(['']);
        });
        
    
    });

  }

  again(){
    window.location.reload();
  }

}
