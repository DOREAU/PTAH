import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';
import { EtatCodePtahService } from './etat-code-ptah.service';
import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { CodeAccesPtahService } from 'app/entities/code-acces-ptah';

@Component({
  selector: 'jhi-etat-code-ptah-update',
  templateUrl: './etat-code-ptah-update.component.html'
})
export class EtatCodePtahUpdateComponent implements OnInit {
  etatCode: IEtatCodePtah;
  isSaving: boolean;

  codeacces: ICodeAccesPtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private etatCodeService: EtatCodePtahService,
    private codeAccesService: CodeAccesPtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ etatCode }) => {
      this.etatCode = etatCode;
    });
    this.codeAccesService.query().subscribe(
      (res: HttpResponse<ICodeAccesPtah[]>) => {
        this.codeacces = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.etatCode.id !== undefined) {
      this.subscribeToSaveResponse(this.etatCodeService.update(this.etatCode));
    } else {
      this.subscribeToSaveResponse(this.etatCodeService.create(this.etatCode));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IEtatCodePtah>>) {
    result.subscribe((res: HttpResponse<IEtatCodePtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCodeAccesById(index: number, item: ICodeAccesPtah) {
    return item.id;
  }
}
