import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { ReviewService } from '../services/review.service';

@Component({
  selector: 'app-prepare-reviewers',
  templateUrl: './prepare-reviewers.component.html',
  styleUrls: ['./prepare-reviewers.component.css']
})
export class PrepareReviewersComponent implements OnInit {

  constructor(private route: ActivatedRoute,protected router: Router, private reviewService: ReviewService) { }

  instance: String='';
  fieldTitle: String='';
  taskId='';
  formFields = [];
  formFieldsDto = null;
  procIn: null;
  enumValues=[];
  title='';
  NO: any;

  ngOnInit() {

    this.instance =  JSON.parse(localStorage.getItem('instance'));

    if(this.route.snapshot.params.mode == 'reviewers'){
      this.title = 'Odabir recenzenata';

      if(this.route.snapshot.params.type == 'date'){
        this.title = 'Postavka roka za recenziju';
      }

      this.reviewService.getTask(this.instance).subscribe(res => {
        this.createForm(res);
  
      });
      
  } 

  }

  onSubmit(value, form){

    let o = new Array();

    if(this.route.snapshot.params.mode == 'reviewers'){

      
      if(this.route.snapshot.params.type == 'date'){

         
      for (var property in value) {
        console.log(property);
        console.log(value[property]);
  
        o.push({fieldId : property, fieldValue : value[property]});
        
      }

        this.reviewService.post(o, this.taskId, 'rokRec').subscribe(r => {

        this.router.navigateByUrl('');

      });
    

      } else {
      
      for (var property in this.NO) {
        console.log('P ' + property);
        console.log('V ' + this.NO[property]);
  
        o.push({fieldId : this.NO[property], fieldValue : this.NO[property]});
        
      }
      if(o.length < 2){
        alert('Morate odabrati minimum 2 recenzenta');
      } else {
      console.log(o);

      this.reviewService.post(o, this.taskId, 'recenzenti').subscribe(r => {

        this.router.navigateByUrl('prepareReviewers/reviewers/date');

      });
    
      }
    }

  



    }

  
    console.log(o);
 
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

}
