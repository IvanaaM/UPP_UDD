import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { Router } from '@angular/router';
import { PaperService } from '../services/paper.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit {

  constructor(private paperService: PaperService, private reviewService: ReviewService, protected router: Router) { }

  log: any;
  instance: any;
  procIn: String;
  formFields = null;
  formFieldsDto = null;
  taskId: null;
  enumValues= [];
  comm: String='';

  ngOnInit() {

    this.log = JSON.parse(localStorage.getItem('logged'));
    this.instance = JSON.parse(localStorage.getItem('instance'));

    this.reviewService.getTaskForReview(this.instance).subscribe(res => {

      this.createForm(res);

      this.paperService.getPdfForPaper(this.taskId).subscribe(r => {

        document.querySelector('iframe').src = r.pdf;

      });

    });
/*
    this.paperService.getEditorComm(this.instance).subscribe(data =>{
      this.comm = data.pdf;
    });
    */
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

      o.push({fieldId : property, fieldValue : value[property]});

      }
      

      this.reviewService.postReview(o, this.formFieldsDto.taskId).subscribe(res => {
        
          this.router.navigateByUrl('');
        });
        
    
      }
}
