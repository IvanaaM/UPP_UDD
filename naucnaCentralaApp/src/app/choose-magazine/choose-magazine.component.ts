import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MagazineService } from '../services/magazine.service';
import { Magazine } from '../modelDTO/magazine';
import { PdfDto } from '../modelDTO/PdfDto';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-choose-magazine',
  templateUrl: './choose-magazine.component.html',
  styleUrls: ['./choose-magazine.component.css']
})
export class ChooseMagazineComponent implements OnInit {

  mode: any;
  
  formFields = [];
  processInstance = "";
  formFieldsDto = null;
  enumValues = [];
  title='';
  NO: any;
  pdfFile: File = null;
  urlPdf: any;
  show: boolean = false;

  constructor(private router: Router, protected route: ActivatedRoute, private userService: UserService, private magazineService: MagazineService) { }

  ngOnInit() {

    this.mode = this.route.snapshot.params.mode;
    console.log("ovo je mode " + this.mode);

    if(this.mode == 'data'){

      this.title = "Unos podataka za rad";
      const procIn = JSON.parse(localStorage.getItem("instance"));


      this.magazineService.getDataForPaper(procIn).subscribe( r => {
        console.log("Res za data" + r);
  
            this.formFields = r.formFields;
            this.formFieldsDto = r;
      
            this.formFields.forEach( (field) =>{
                
              if( field.type.name=='enum'){
                this.enumValues = Object.keys(field.type.values);
              }
            });
            
      });

    } else {

      this.title = 'Odabir casopisa';

      this.magazineService.getMagazines().subscribe( res => {
        console.log(res);
  
            this.formFields = res.formFields;
            this.formFieldsDto = res;
            this.processInstance = res.processInstanceId;

            localStorage.setItem('instance', JSON.stringify(res.processInstanceId));
      
            this.formFields.forEach( (field) =>{
                
              if( field.type.name=='enum'){
                this.enumValues = Object.keys(field.type.values);
              }
            });
      });
    }
  }

  onSubmit(value, form){

    let o = new Array();
    console.log(value);
    for (var property in value) {
      console.log(property);
    //  if(property == 'pdf'){
        
    //  o.push({fieldId : property, fieldValue : this.urlPdf});
    //  } else {
      o.push({fieldId : property, fieldValue : value[property]});
     // }
    }

    if(this.mode == 'data'){
      
      const pdfModel = new PdfDto(this.urlPdf);

      this.magazineService.postPdfBytes(JSON.parse(localStorage.getItem('instance')), pdfModel).subscribe(r => {

        console.log(r);

      this.magazineService.postMagazineData(o, this.formFieldsDto.taskId, 'paper').subscribe(res => {
        
          this.router.navigateByUrl('addCoauthors');
          // nakon ovoga urednik se loguje da proveri da li je tematski prihvatljiv rad
        });
        
      });

    } else {
   
      this.magazineService.postChooseMagazine(o, this.formFieldsDto.taskId, 'chooseMagazine').subscribe(res => {

      const procIn = JSON.parse(localStorage.getItem('instance'))
      this.userService.shouldPay(procIn).subscribe(r => {
        console.log(r);
        // treba da plati
        if(r == true){
            this.router.navigateByUrl('paying');
        } else {
          this.router.navigateByUrl('chooseMagazine/data');
        }

      });

      this.router.navigateByUrl('/chooseMagazine/data');


    });
  }
}

handlePDF(event){

    var reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    this.pdfFile = <File>event.target.files[0];

    reader.onload = (e) => {
      this.urlPdf = e.target['result'];
      console.log(this.urlPdf);
      
    //document.querySelector('iframe').src = this.urlPdf;
    }

}

}
