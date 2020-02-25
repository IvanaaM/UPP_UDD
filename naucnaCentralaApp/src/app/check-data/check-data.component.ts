import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EditorService } from '../services/editor.service';

@Component({
  selector: 'app-check-data',
  templateUrl: './check-data.component.html',
  styleUrls: ['./check-data.component.css']
})
export class CheckDataComponent implements OnInit {

  magazine: any;
  formFields = [];
  formFieldsDto = null;
  enumValues = null;
  editors: [];

  constructor(protected router: Router, private editorService: EditorService) {

    const procIn = JSON.parse(localStorage.getItem('instance'));
    const logged = JSON.parse(localStorage.getItem('logged'));

    this.editorService.getMagazine(procIn).subscribe(res => {
this.magazine = res;
this.editors = this.magazine.editors;
    });

    editorService.getTask(procIn).subscribe(res => {
      console.log(res);

      this.formFields = res.formFields;
      
      this.formFieldsDto = res;

      this.formFields.forEach( (field) =>{
          
        if( field.type.name=='enum'){
          this.enumValues = Object.keys(field.type.values);
        }
      });
      
  });

}

  ngOnInit() {
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      o.push({fieldId : property, fieldValue : value[property]});
      
    }

    console.log(o);
  
    this.editorService.setEditors(o, this.formFieldsDto.taskId, 'provera').subscribe(res => {
      
      localStorage.removeItem('zadatakAdmin');
    this.router.navigateByUrl('/');

    });
   
  }
}
