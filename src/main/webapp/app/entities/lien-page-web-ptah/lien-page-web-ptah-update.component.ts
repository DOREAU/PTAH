import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';
import { LienPageWebPtahService } from './lien-page-web-ptah.service';
import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';
import { PageWebPtahService } from 'app/entities/page-web-ptah';

@Component({
  selector: 'jhi-lien-page-web-ptah-update',
  templateUrl: './lien-page-web-ptah-update.component.html'
})
export class LienPageWebPtahUpdateComponent implements OnInit {
  lienPageWeb: ILienPageWebPtah;
  isSaving: boolean;

  pagewebs: IPageWebPtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private lienPageWebService: LienPageWebPtahService,
    private pageWebService: PageWebPtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lienPageWeb }) => {
      this.lienPageWeb = lienPageWeb;
    });
    this.pageWebService.query().subscribe(
      (res: HttpResponse<IPageWebPtah[]>) => {
        this.pagewebs = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.lienPageWeb.id !== undefined) {
      this.subscribeToSaveResponse(this.lienPageWebService.update(this.lienPageWeb));
    } else {
      this.subscribeToSaveResponse(this.lienPageWebService.create(this.lienPageWeb));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ILienPageWebPtah>>) {
    result.subscribe((res: HttpResponse<ILienPageWebPtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPageWebById(index: number, item: IPageWebPtah) {
    return item.id;
  }
}
