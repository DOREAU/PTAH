import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';
import { ScenarioPtahService } from './scenario-ptah.service';
import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';
import { EquipePtahService } from 'app/entities/equipe-ptah';

@Component({
  selector: 'jhi-scenario-ptah-update',
  templateUrl: './scenario-ptah-update.component.html'
})
export class ScenarioPtahUpdateComponent implements OnInit {
  scenario: IScenarioPtah;
  isSaving: boolean;

  equipes: IEquipePtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private scenarioService: ScenarioPtahService,
    private equipeService: EquipePtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ scenario }) => {
      this.scenario = scenario;
    });
    this.equipeService.query().subscribe(
      (res: HttpResponse<IEquipePtah[]>) => {
        this.equipes = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.scenario.id !== undefined) {
      this.subscribeToSaveResponse(this.scenarioService.update(this.scenario));
    } else {
      this.subscribeToSaveResponse(this.scenarioService.create(this.scenario));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IScenarioPtah>>) {
    result.subscribe((res: HttpResponse<IScenarioPtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEquipeById(index: number, item: IEquipePtah) {
    return item.id;
  }
}
