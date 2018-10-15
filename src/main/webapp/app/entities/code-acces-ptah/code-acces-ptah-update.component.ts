import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { CodeAccesPtahService } from './code-acces-ptah.service';
import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';
import { ScenarioPtahService } from 'app/entities/scenario-ptah';

@Component({
  selector: 'jhi-code-acces-ptah-update',
  templateUrl: './code-acces-ptah-update.component.html'
})
export class CodeAccesPtahUpdateComponent implements OnInit {
  codeAcces: ICodeAccesPtah;
  isSaving: boolean;

  scenarios: IScenarioPtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private codeAccesService: CodeAccesPtahService,
    private scenarioService: ScenarioPtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ codeAcces }) => {
      this.codeAcces = codeAcces;
    });
    this.scenarioService.query().subscribe(
      (res: HttpResponse<IScenarioPtah[]>) => {
        this.scenarios = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.codeAcces.id !== undefined) {
      this.subscribeToSaveResponse(this.codeAccesService.update(this.codeAcces));
    } else {
      this.subscribeToSaveResponse(this.codeAccesService.create(this.codeAcces));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICodeAccesPtah>>) {
    result.subscribe((res: HttpResponse<ICodeAccesPtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackScenarioById(index: number, item: IScenarioPtah) {
    return item.id;
  }
}
