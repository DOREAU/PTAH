import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';
import { PageWebPtahService } from './page-web-ptah.service';
import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';
import { ScenarioPtahService } from 'app/entities/scenario-ptah';

@Component({
  selector: 'jhi-page-web-ptah-update',
  templateUrl: './page-web-ptah-update.component.html'
})
export class PageWebPtahUpdateComponent implements OnInit {
  pageWeb: IPageWebPtah;
  isSaving: boolean;

  scenarios: IScenarioPtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private pageWebService: PageWebPtahService,
    private scenarioService: ScenarioPtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pageWeb }) => {
      this.pageWeb = pageWeb;
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
    if (this.pageWeb.id !== undefined) {
      this.subscribeToSaveResponse(this.pageWebService.update(this.pageWeb));
    } else {
      this.subscribeToSaveResponse(this.pageWebService.create(this.pageWeb));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IPageWebPtah>>) {
    result.subscribe((res: HttpResponse<IPageWebPtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
