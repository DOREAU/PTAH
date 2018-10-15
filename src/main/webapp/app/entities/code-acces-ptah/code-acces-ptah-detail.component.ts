import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';

@Component({
  selector: 'jhi-code-acces-ptah-detail',
  templateUrl: './code-acces-ptah-detail.component.html'
})
export class CodeAccesPtahDetailComponent implements OnInit {
  codeAcces: ICodeAccesPtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ codeAcces }) => {
      this.codeAcces = codeAcces;
    });
  }

  previousState() {
    window.history.back();
  }
}
