import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';

@Component({
  selector: 'jhi-scenario-ptah-detail',
  templateUrl: './scenario-ptah-detail.component.html'
})
export class ScenarioPtahDetailComponent implements OnInit {
  scenario: IScenarioPtah;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ scenario }) => {
      this.scenario = scenario;
    });
  }

  previousState() {
    window.history.back();
  }
}
