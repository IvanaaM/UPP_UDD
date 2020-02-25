import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PaperService } from '../services/paper.service';
import { MagazineService } from '../services/magazine.service';
import { UserService } from '../services/user.service';
import { EditorService } from '../services/editor.service';

@Component({
  selector: 'app-set-date',
  templateUrl: './set-date.component.html',
  styleUrls: ['./set-date.component.css']
})
export class SetDateComponent implements OnInit {

  formFields = null;
  formFieldsDto = null;
  taskId: null;
  procIn: null;
  enumValues= [];
  logged: any;

  constructor(protected router: Router, private editorService: EditorService, private userService: UserService) { }

  ngOnInit() {

    this.logged = JSON.parse(localStorage.getItem('logged'));
    this.procIn = JSON.parse(localStorage.getItem('instance'));

    this.editorService.getNext(this.procIn).subscribe(res => {
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

      }

      o.push({fieldId : property, fieldValue : value[property]});


      
this.editorService.post(o, this.taskId, 'rok').subscribe(res => {

  this.router.navigateByUrl('');

});
      }


}
