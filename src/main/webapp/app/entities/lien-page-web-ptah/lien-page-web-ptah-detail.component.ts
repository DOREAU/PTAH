import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

@Component({
  selector: 'jhi-lien-page-web-ptah-detail',
  templateUrl: './lien-page-web-ptah-detail.component.html'
})
export class LienPageWebPtahDetailComponent implements OnInit {
  lienPageWeb: ILienPageWebPtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lienPageWeb }) => {
      this.lienPageWeb = lienPageWeb;
    });
  }

  previousState() {
    window.history.back();
  }
}
