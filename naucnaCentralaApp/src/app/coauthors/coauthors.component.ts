import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReviewDTO } from '../modelDTO/ReviewDTO';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-coauthors',
  templateUrl: './coauthors.component.html',
  styleUrls: ['./coauthors.component.css']
})
export class CoauthorsComponent implements OnInit {


  log: any;
  instance: any;
  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];
  comm: String='';
  list: ReviewDTO[];
  more: boolean = false;
  name: String='';

  constructor(private userService: UserService, protected router: Router) { }

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('logged'));
    this.instance = JSON.parse(localStorage.getItem('instance'));

    this.userService.getTaskForUser(this.instance).subscribe(res => {
      this.createForm(res);
    })
  }

  createForm(res){

    this.formFields = res.formFields;
    this.procIn = res.processInstanceId;
    this.formFieldsDto = res;
  
    this.taskId = res.taskId;
  
    this.formFields.forEach( (field) =>{
      
      if( field.type.name=='enum'){
        this.enumValues = Object.keys(field.type.values);
      }
    });
  
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      if(property == 'imeK'){
        this.name = value[property];
      }

      if(property == 'josKoautora'){
        if(value[property] == true){
          this.more = true;
        }
      }

      o.push({fieldId : property, fieldValue : value[property]});

      }

      console.log('Ovo je ime koje saljem ' + this.name);
      this.userService.postCoauthor(o, this.taskId, this.name).subscribe(r => {

        if(this.more == true){
          window.location.reload();
        } else {
          this.router.navigateByUrl('');
        }
      });
      
  }

}
