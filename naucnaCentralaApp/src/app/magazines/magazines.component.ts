import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MagazineService } from '../services/magazine.service';
import { Magazine } from '../modelDTO/magazine';

@Component({
  selector: 'app-magazines',
  templateUrl: './magazines.component.html',
  styleUrls: ['./magazines.component.css']
})
export class MagazinesComponent implements OnInit {

  magazines: Magazine[];

  constructor(protected router: Router, private magazineService: MagazineService) { }

  ngOnInit() {

    this.magazineService.getMagazines().subscribe(res => {

      this.magazines = res;
      console.log(this.magazines);
    });

  }

  createPaper(id: number){
      this.router.navigateByUrl('createPaper/' + id);
  
  }

}
