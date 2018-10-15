import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';
import { EquipePtahService } from './equipe-ptah.service';
import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { CodeAccesPtahService } from 'app/entities/code-acces-ptah';

@Component({
  selector: 'jhi-equipe-ptah-update',
  templateUrl: './equipe-ptah-update.component.html'
})
export class EquipePtahUpdateComponent implements OnInit {
  equipe: IEquipePtah;
  isSaving: boolean;

  codeacces: ICodeAccesPtah[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private equipeService: EquipePtahService,
    private codeAccesService: CodeAccesPtahService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.equipe = equipe;
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
    if (this.equipe.id !== undefined) {
      this.subscribeToSaveResponse(this.equipeService.update(this.equipe));
    } else {
      this.subscribeToSaveResponse(this.equipeService.create(this.equipe));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IEquipePtah>>) {
    result.subscribe((res: HttpResponse<IEquipePtah>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
