import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { MagazineService } from '../services/magazine.service';
import { PdfDto } from '../modelDTO/PdfDto';
import { PaperService } from '../services/paper.service';
import { ReviewDTO } from '../modelDTO/ReviewDTO';
import { EditorService } from '../services/editor.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private route: ActivatedRoute, private editorService: EditorService, protected router: Router,private paperService: PaperService, private magazineService: MagazineService, private userService: UserService) { }

  logged: any;
  instance: String;
  title: String;
  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];
  pdfFile: File = null;
  urlPdf: any;
  comm: String= '';
  paper = [];
  show: boolean = true;
  comms: ReviewDTO[];
  coa: [];
  rec: boolean = true;

  ngOnInit() {

    this.logged = JSON.parse(localStorage.getItem('logged'));
    this.instance = JSON.parse(localStorage.getItem('instance'));


    if(localStorage.getItem('rec') == null) 
    {
      this.show = true;
      this.rec = true;
    this.userService.getTaskForUser(this.instance).subscribe(res => {
      console.log(res);
      this.createForm(res);

        if(res == null){
            alert('Rok za ispravku rada je istekao');
         } else {
            this.title = 'Ispravka PDF rada';
      
            this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

              console.log(r);

              document.querySelector('iframe').src = r.pdf;
                        
                this.paperService.getEditorComm(this.instance).subscribe(data =>{
                
                   this.comm = data.pdf;

                   this.paperService.getPaperData(this.instance).subscribe(f => {
                    console.log("Ovo je rad"  + f);
                      this.paper = f;
                    
                      this.editorService.getCoauthors(this.instance).subscribe(r => {
                          this.coa = r;
                      });
                   });

              });
      });
      }
    });

  } else {
    this.rec = false;
    // ispravka rada nakon recenziranja
    this.show = false;
    this.title = 'Ispravka PDF rada';
    this.userService.getTaskForUser(this.instance).subscribe(res => {
      this.createForm(res);
      if(res == null){
        alert('Isteklo je vreme za korigovanje');
      } else {

      this.userService.getComments(this.instance).subscribe(r => {  
          this.comms = r;
      });

      this.paperService.getPdfForPaper(this.taskId).subscribe(re => {

        document.querySelector('iframe').src = re.pdf;
      });
    }
      // treba prikazati komentare recenzenata
    });
  }
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      o.push({fieldId : property, fieldValue : value[property]});

      }

      if(localStorage.getItem('rec') == null) 
      {

      const pdfModel = new PdfDto(this.urlPdf);

      this.magazineService.postPdfBytes(JSON.parse(localStorage.getItem('instance')), pdfModel).subscribe(r => {

        console.log(r);
  
    for (let pap in this.paper) {
      console.log(pap);

      //o.push({fieldId : pap.fieldId, fieldValue : pap.fieldValue});

    }

      this.magazineService.postMagazineData(o, this.formFieldsDto.taskId, 'pdfChanged').subscribe(res => {
        
        // korisnik je izmenio rad
          this.router.navigateByUrl('');
        });
        
      });

    } else {
      
      const pdfModel = new PdfDto(this.urlPdf);

      this.userService.postNO(o, this.taskId, 'changedPDF').subscribe(re => {

        this.magazineService.postPdfBytes(JSON.parse(localStorage.getItem('instance')), pdfModel).subscribe(r => {

          console.log(r);
          localStorage.removeItem('rec');
          this.router.navigateByUrl('');
    
        });
      });

      // ovde treba poslati pdf izmenjeni nakon recenzija

    }
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

handlePDF(event){

  var reader = new FileReader();
  reader.readAsDataURL(event.target.files[0]);
  console.log(reader);
  this.pdfFile = <File>event.target.files[0];

  reader.onload = (e) => {
    this.urlPdf = e.target['result'];
   // console.log(this.urlPdf);
    
  //document.querySelector('iframe').src = this.urlPdf;
  }

}
  
  

}
