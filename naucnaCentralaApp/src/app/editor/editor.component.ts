import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EditorService } from '../services/editor.service';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  mode: any;
  show: boolean = false;
  taskId='';
  formFields = [];
  formFieldsDto = null;
  pi='';
  enumValues = [];
  naucObl: any;
  NO: any;
  show2 = false;

  constructor(protected router: Router, private route: ActivatedRoute, private editorService: EditorService) { 

    this.mode = this.route.snapshot.params.mode;
    const procIn = JSON.parse(localStorage.getItem('instance'));
    const logged = JSON.parse(localStorage.getItem('logged'));

    if(this.mode == 'add'){
      this.show = true;

    } else {
      this.show = false;
    }

    /*
    editorService.getTask(procIn).subscribe(res => {
       
      this.formFields = res.formFields;
        
        this.pi = res.processInstanceId;
        this.formFieldsDto = res;
  
        this.formFields.forEach( (field) =>{
            
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      });
*/
  }

  ngOnInit() {
  }

  createMagazine(){
    this.router.navigateByUrl('/register/createMagazine');
  }

  mainEditing(){
    this.router.navigateByUrl('/mainEditor');
  }


  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      if (property == 'urednici'){
        for(let a in this.NO){
          o.push({fieldId: value[property][a], fieldValue: value[property][a]})
        }
      } else {

      o.push({fieldId : property, fieldValue : value[property]});
      }
    }

    console.log(o);
    if(this.mode == 'add'){
           
    this.editorService.setEditors(o, this.formFieldsDto.taskId, 'urednici').subscribe(res => {
      
      localStorage.setItem('zadatakAdmin', JSON.stringify("/checkData"));
      this.router.navigateByUrl('/');

    });
    }else {
 
  }
  }

  reviewCheck(){
    this.router.navigateByUrl('checkReviews');
  }

  setDate(){
    this.router.navigateByUrl('setDate');
  }

  prepare(){
    this.router.navigateByUrl('prepareReviewers/reviewers');
  }

}
