import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ReviewService } from '../services/review.service';
import { PaperService } from '../services/paper.service';
import { EditorService } from '../services/editor.service';
import { ReviewDTO } from '../modelDTO/ReviewDTO';
import { MagazineService } from '../services/magazine.service';

@Component({
  selector: 'app-check-reviews',
  templateUrl: './check-reviews.component.html',
  styleUrls: ['./check-reviews.component.css']
})
export class CheckReviewsComponent implements OnInit {
  constructor(private route: ActivatedRoute, private paperService: PaperService, private editorService: EditorService, private reviewService: ReviewService, protected router: Router) { }

  log: any;
  instance: any;
  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];
  comm: String='';
  list: ReviewDTO[];
  next: boolean =false;
  show: boolean = true;

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('logged'));
    this.instance = JSON.parse(localStorage.getItem('instance'));


    if(this.route.snapshot.params.mode != 'date'){
      
      this.show = true;
    this.editorService.getNext(this.instance).subscribe(res =>{

      this.createForm(res);

      this.paperService.getPdfForPaper(this.taskId).subscribe(r2 => {

        document.querySelector('iframe').src = r2.pdf;
      });

      this.reviewService.getAllReviews(this.instance).subscribe(r => {

            this.list = r;
            console.log(this.list);
      }); 

    });
  } else {
    this.show = false;
    this.editorService.getNext(this.instance).subscribe(res =>{

      this.createForm(res);
    });
  }
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

      if(value[property] == 'daUMI' || value[property] == 'daUUVI'){

          this.next = true;
      }

      if(value[property] == 'daUMI'){

        localStorage.setItem('manjeIzmene', JSON.stringify(true));
    }

      o.push({fieldId : property, fieldValue : value[property]});

      }

      if (this.route.snapshot.params.mode != 'date'){

      this.editorService.post(o, this.taskId, 'decision').subscribe(r => {

        if (this.next == true){
          //postavka roka
          this.router.navigateByUrl('checkReviews/date');
        } else {

          localStorage.removeItem('instance');
          this.router.navigateByUrl('');
        }
      });
    } else {
      localStorage.setItem('rec', JSON.stringify(true));
      this.editorService.post(o, this.taskId, 'deadlines').subscribe(r => {
        this.router.navigateByUrl('');
      });
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
