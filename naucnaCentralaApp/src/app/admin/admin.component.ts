import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { AdminServiceService } from '../services/admin-service.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  message: String;
  show: boolean = true;
  formFields = [];
  formFieldsDto  = null;
  enumValues = [];
  name: any;

  constructor(private adminService: AdminServiceService, protected router: Router) { 

    this.adminService.getRec().subscribe( res => {
console.log(res);
      if (res == null){
          console.log('Registrovani korisnik ne zeli da bude recenzent');
          
      } else {
          console.log(res);
          this.show = false;

          this.formFields = res.formFields;
          this.formFieldsDto = res;
    
          this.formFields.forEach( (field) =>{
              
            if( field.type.name=='enum'){
              this.enumValues = Object.keys(field.type.values);
            }
          });
      }

      this.adminService.getUser().subscribe( res1 => {
        //alert(res1);
          this.name = res1;
      })

    });

    const g = localStorage.getItem('zadatakAdmin');

    if(g != null){
      this.router.navigateByUrl('/checkData');
    }
  }

  ngOnInit() {
  }

  onSubmit(value, form){

    let jos = false;
console.log("aaaa");
    let o = [];
    let noList = [];

    console.log(value);

    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      o.push({fieldId : property, fieldValue : value[property]});
      }

    this.adminService.saveRec(o, this.formFieldsDto.taskId).subscribe( res => {
      console.log(res);
      
      this.show = true;

    });

  }

}
