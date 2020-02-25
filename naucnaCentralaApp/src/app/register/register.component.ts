import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository.service';
import { UserService } from '../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { EditorService } from '../services/editor.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

   formFields = [];
   processInstance = "";
   formFieldsDto = null;
   enumValues = [];
   NO: any;
   mode: any;
   fieldTitle: any;
   urednici: boolean = false;

  constructor(private userService : UserService, private editorService: EditorService, private route: ActivatedRoute, private repositoryService : RepositoryService, protected router: Router) { 

      this.mode = this.route.snapshot.params.mode;

      if (this.mode == 'createMagazine'){
        this.fieldTitle = "Kreiranje casopisa";

        repositoryService.startProcessMagazine().subscribe(res => {

          this.createForm(res);

        });
      } else if (this.mode == 'user') {

        this.fieldTitle = "Registracija";

        repositoryService.startProcess().subscribe(res => {
        
        this.createForm(res);
    }
      );

  } else if ( this.mode == 'magazineReviewers'){
    const procIn = JSON.parse(localStorage.getItem("instance"));

    this.fieldTitle = "Dodavanje recenzenata za casopis";

        editorService.getTask(procIn).subscribe(res => {
        
        this.createForm(res);
    }
      );

  } else if (this.mode == 'magazineEditors'){
  //  window.location.reload();
    this.fieldTitle = "Dodavanje urednika za casopis";
    const procIn = JSON.parse(localStorage.getItem("instance"));

        editorService.getTask(procIn).subscribe(res => {
        
        this.createForm(res);
    }
      );
  }


  }

  ngOnInit() {
  }

  onSubmit(value, form){

    let jos = false;

    let o = [];
    let noList = [];

    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      if(property == "pitanjeUrednici"){
        if(value[property] == true)
        this.urednici = true;
      }

      if (property == 'recenzenti'){
        for(let a in this.NO){
          o.push({fieldId: value[property][a], fieldValue: value[property][a]})
        }
      } else {

      o.push({fieldId : property, fieldValue : value[property]});
      }
      }

    console.log(o);
    console.log(this.processInstance);

    
    if (this.mode == 'createMagazine'){

      o.push({fieldId : 'logged', fieldValue : JSON.parse(localStorage.getItem('logged')).username});

      this.editorService.registerMagazine(o, this.formFieldsDto.taskId, "casopis").subscribe(res => {
          //alert('Uspesno kreiran casopis');
          this.router.navigateByUrl('/unosOblasti/casopis');
      });
    } else if (this.mode == 'user') {

    this.userService.registerUser(o, this.formFieldsDto.taskId, "registration").subscribe( res => {
      console.log(res);
      
      this.router.navigateByUrl('/unosOblasti');

    });

    } else if (this.mode == 'magazineReviewers'){

      this.editorService.setReviewers(o, this.formFieldsDto.taskId, 'reviewers').subscribe( res => {
        console.log(res);
        
        if(this.urednici == true){
          this.router.navigateByUrl('/editor/add');

        } else {
          localStorage.setItem('zadatakAdmin', JSON.stringify("/checkData"));
          this.router.navigateByUrl('/');
        }
  
      });

    } else if (this.mode == 'magazineEditors'){

      this.editorService.setReviewers(o, this.formFieldsDto.taskId, 'editors').subscribe( res => {
        console.log(res);
        
  
      });
    }
  
  }

createForm(res){

  this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFieldsDto = res;

        localStorage.setItem('instance', JSON.stringify(this.processInstance));

        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });

}

}
