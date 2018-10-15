import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';

@Component({
  selector: 'jhi-page-web-ptah-detail',
  templateUrl: './page-web-ptah-detail.component.html'
})
export class PageWebPtahDetailComponent implements OnInit {
  pageWeb: IPageWebPtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pageWeb }) => {
      this.pageWeb = pageWeb;
    });
  }

  previousState() {
    window.history.back();
  }
}
