import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-paying',
  templateUrl: './paying.component.html',
  styleUrls: ['./paying.component.css']
})
export class PayingComponent implements OnInit {

  constructor(protected router: Router, private userService: UserService) { }

  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];

  ngOnInit() {

    this.procIn = JSON.parse(localStorage.getItem('instance'));

    this.userService.getTaskForUser(this.procIn).subscribe(res => {

      this.createForm(res);

    });
    

  }

createForm(res){

  this.formFields = res.formFields;
  this.procIn = res.processInstanceId;
  this.formFieldsDto = res;

  this.taskId = res.taskId;

  //localStorage.setItem('instance', JSON.stringify(this.procIn));

  this.formFields.forEach( (field) =>{
    
    if( field.type.name=='enum'){
      this.enumValues = Object.keys(field.type.values);
    }
  });

}

onSubmit(value, form){

  let o = new Array();
  console.log(value);
  for (var property in value) {
    console.log(property);

    o.push({fieldId : property, fieldValue : value[property]});

  }

  this.userService.postNO(o,this.taskId, "paying").subscribe(res => {

    this.router.navigateByUrl('chooseMagazine/data');

  });
}

}
