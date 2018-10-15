import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { Principal } from 'app/core';
import { CodeAccesPtahService } from './code-acces-ptah.service';

@Component({
  selector: 'jhi-code-acces-ptah',
  templateUrl: './code-acces-ptah.component.html'
})
export class CodeAccesPtahComponent implements OnInit, OnDestroy {
  codeAcces: ICodeAccesPtah[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private codeAccesService: CodeAccesPtahService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private principal: Principal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.codeAccesService
        .search({
          query: this.currentSearch
        })
        .subscribe(
          (res: HttpResponse<ICodeAccesPtah[]>) => (this.codeAcces = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.codeAccesService.query().subscribe(
      (res: HttpResponse<ICodeAccesPtah[]>) => {
        this.codeAcces = res.body;
        this.currentSearch = '';
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCodeAcces();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICodeAccesPtah) {
    return item.id;
  }

  registerChangeInCodeAcces() {
    this.eventSubscriber = this.eventManager.subscribe('codeAccesListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
