import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PaperService } from '../services/paper.service';
import { EditorService } from '../services/editor.service';
import { UserService } from '../services/user.service';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-main-editor-page',
  templateUrl: './main-editor-page.component.html',
  styleUrls: ['./main-editor-page.component.css']
})
export class MainEditorPageComponent implements OnInit {

  requests = null;
  list = [];
  formFields = null;
  formFieldsDto = null;
  a: null;
  procIn = "";
  enumValues = [];
  ff = null;
  paper = [];
  taskId = '';
  formatGood: boolean = false;
  mode: any;
  instance: String ='';
  pdfShow: boolean = false;
  title: String = '';
  pdfGood: boolean = false;
  dataShow: boolean = false;
  coa: [];
  pdfShow2: boolean = true;
  accept: boolean = false;
  NO: any;
  showCoauthors: boolean = false;

  constructor(private reviewService: ReviewService, protected router: Router, private route: ActivatedRoute, private userService: UserService, private paperService: PaperService, private editorService: EditorService) { }

  // stranica glavnog urednika: treba da mu se prikazu zadaci za obradu radova:
  // dakle, sve AKTIVNE INSTANCE procesa za obradu teksta koje se odnose na
  // trenutno ulogovanog urednika

  ngOnInit() {
    

    this.instance = JSON.parse(localStorage.getItem('instance'));


    this.mode = this.route.snapshot.params.mode;

    if(this.mode == 'Pdf'){
      this.showCoauthors = false;

      if(this.route.snapshot.params.type == 'setD'){

        this.title = 'Postavka roka + komentar';
        this.pdfShow = true;
        this.dataShow = false;

        this.userService.checkHasTasks(this.instance).subscribe(data => {

          console.log(data);
          this.taskId =data.taskId;
          this.createForm(data);
        });


      } else{

      this.title = 'Provera PDF-a';
      this.pdfShow = true;
      this.dataShow = true;

      this.paperService.getPdfTaskCheck(this.instance).subscribe(res => {
        
      this.taskId = res.taskId;
      this.createForm(res);

      this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

      console.log(r);
         
       document.querySelector('iframe').src = r.pdf;

      });


      });
    }


   } else {

    if( JSON.parse(localStorage.getItem('manjeIzmene')) == null){

      this.pdfShow = true;
      this.dataShow = false;
      this.showCoauthors = true;
      this.title="Provera podataka rada";

      this.editorService.getCoauthors(this.instance).subscribe(r => {
          this.coa = r;
      });

    this.paperService.getPapersForIds(this.instance).subscribe(res => {
      console.log(res);

      this.taskId = res.taskId;

      this.createForm(res);

      this.paperService.getPaperData(this.instance).subscribe(r => {
        console.log(r);
        this.paper = r;
      });
    
    });

    } else {
      this.pdfShow = true;
      this.title = 'Provera rada';
      this.dataShow = true;
      this.pdfShow2 = false;
      this.showCoauthors = false;
      //slucaj gde urednik donosi konacnu odluku, bila je zahtevana manja izmena rada
      //pokazati i komentare recenzenata

      // dodati i komentar korisnika
      this.editorService.getNext(this.instance).subscribe(re => {

        this.createForm(re);
        this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

          document.querySelector('iframe').src = r.pdf;

          this.reviewService.getAllReviews(this.instance).subscribe(res => {

            this.list = res;

              this.paperService.getPdfForPaper(this.taskId).subscribe(re => {
                document.querySelector('iframe').src = re.pdf;
              })

          });
        });

      });
    }

      
     
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

onSubmit(value, form){
  let o = new Array();
  for (var property in value) {
    console.log(property);
    console.log(value[property]);

    if(property == 'tematskiPrihvatljiv'){
      if(value[property]==true){
        this.formatGood = true;
      }
    }
    if(property == 'dobroFormatiran'){
      if(value[property]==true){
        this.pdfGood = true;
      }
    }

    // hoce ponovo doradu
    if(value[property] == 'konDa'){
      this.accept = true;
      console.log("jesteee");
    }

    if(value[property] == 'konNE'){
        this.accept = false;
        console.log("nijeeee");
    }
    o.push({fieldId : property, fieldValue : value[property]});
    }

if (this.mode == 'Pdf'){

  if(this.route.snapshot.params.type == 'setD'){
    this.paperService.post(this.taskId, o, 'deadlines').subscribe(r => {
     
      this.router.navigateByUrl('');
    });
  } else {

    this.paperService.post(this.taskId, o, 'dobarFormatPDF').subscribe(r => {
      if(this.pdfGood != true){
        
        this.router.navigateByUrl('mainEditor/Pdf/setD'); 
      } else {
        
        // Rad je dobar i ide na recenziranje, sad je loguje urednikNaucne oblasti
        this.router.navigateByUrl(''); 
      }
    });

  }

} else {

  if (JSON.parse(localStorage.getItem('manjeIzmene')) != null){


    this.editorService.post(o, this.taskId, 'konacnaOdl').subscribe(res =>{

      console.log('usao je u kraj');
        if(this.accept == true){
          console.log('kraj procesa');
          localStorage.clear();
          this.router.navigateByUrl('');
        } else {
          localStorage.setItem('rec', JSON.stringify(true));
         // localStorage.removeItem('rec');
         // localStorage.removeItem('manje izmene');
          this.router.navigateByUrl('setDate');
        }

    });
} else {


   this.paperService.post(this.taskId, o, 'temPrihvatljiv').subscribe(res => {
    console.log(res);
    if(this.formatGood == true){
    this.router.navigateByUrl('mainEditor/Pdf');
    } else {
      this.router.navigateByUrl('');
    }
  });
}
}


  }

  handleRecomm(str: String){

    if (str == 'prep1'){
       return 'Prihvata se';
    } else if (str == 'prep2'){
       return 'Prihvata se uz manje ispravke';
    } else if (str == 'prep3'){
       return 'Prihvata se uslovno uz vece ispravke';
    } else {
       return 'Odbija se';
    }
}

}







