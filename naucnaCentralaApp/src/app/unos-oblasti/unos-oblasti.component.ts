import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RepositoryService } from '../services/repository.service';
import { EditorService } from '../services/editor.service';
import { EditorComponent } from '../editor/editor.component';

@Component({
  selector: 'app-unos-oblasti',
  templateUrl: './unos-oblasti.component.html',
  styleUrls: ['./unos-oblasti.component.css']
})
export class UnosOblastiComponent implements OnInit {

  taskId='';
  formFields = [];
  formFieldsDto = null;
  pi='';
  enumValues = [];
  show = false;
  naucObl: any;
  mode: any;
  show2 = false;

  constructor(private userService: UserService, private editorService: EditorService, private route: ActivatedRoute, private router: Router, private repositoryService: RepositoryService) {
    
    this.mode = this.route.snapshot.params.mode;
    const procIn = JSON.parse(localStorage.getItem('instance'));

    if (this.mode == 'casopis'){

      editorService.getTask(procIn).subscribe(res => {
        console.log(res);

        this.formFields = res.formFields;
        
        this.pi = res.processInstanceId;
        this.formFieldsDto = res;
  
        this.formFields.forEach( (field) =>{
            
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
        
    }
      );

    } else {

      repositoryService.getTask(procIn).subscribe(res => {

        this.formFields = res.formFields;
        this.pi = res.processInstanceId;
        this.formFieldsDto = res;
  
        this.formFields.forEach( (field) =>{
            
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
    }
      );
    }
    
    
   }

  ngOnInit() {
  }

  onSubmit(value, form){

    let jos = false;
    let o = new Array();
    console.log(value);
    for (var property in this.naucObl) {
      console.log(property);
      console.log(this.naucObl[property]);
      o.push({fieldId : this.naucObl[property], fieldValue : this.naucObl[property]});

    }

    console.log(o);

    if (this.mode == 'casopis'){

      this.editorService.postNO(o, this.formFieldsDto.taskId, 'naucneOblastiCasopis').subscribe( res => {
        console.log(res);
     
        this.show = false;
        this.show2 = true;

        this.router.navigateByUrl('register/magazineReviewers');
  
    });

    } else {
      
    this.userService.postNO(o, this.formFieldsDto.taskId, "naucneOblasti").subscribe( res => {
      console.log(res);
   
      this.show = true;
    

  });
}
}
}

