import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';

@Component({
  selector: 'jhi-equipe-ptah-detail',
  templateUrl: './equipe-ptah-detail.component.html'
})
export class EquipePtahDetailComponent implements OnInit {
  equipe: IEquipePtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.equipe = equipe;
    });
  }

  previousState() {
    window.history.back();
  }
}
