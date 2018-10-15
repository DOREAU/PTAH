import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

@Component({
  selector: 'jhi-etat-code-ptah-detail',
  templateUrl: './etat-code-ptah-detail.component.html'
})
export class EtatCodePtahDetailComponent implements OnInit {
  etatCode: IEtatCodePtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ etatCode }) => {
      this.etatCode = etatCode;
    });
  }

  previousState() {
    window.history.back();
  }
}
