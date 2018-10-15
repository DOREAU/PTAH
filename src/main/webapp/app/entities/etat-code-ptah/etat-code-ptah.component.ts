import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';
import { Principal } from 'app/core';
import { EtatCodePtahService } from './etat-code-ptah.service';

@Component({
  selector: 'jhi-etat-code-ptah',
  templateUrl: './etat-code-ptah.component.html'
})
export class EtatCodePtahComponent implements OnInit, OnDestroy {
  etatCodes: IEtatCodePtah[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private etatCodeService: EtatCodePtahService,
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
      this.etatCodeService
        .search({
          query: this.currentSearch
        })
        .subscribe(
          (res: HttpResponse<IEtatCodePtah[]>) => (this.etatCodes = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.etatCodeService.query().subscribe(
      (res: HttpResponse<IEtatCodePtah[]>) => {
        this.etatCodes = res.body;
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
    this.registerChangeInEtatCodes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEtatCodePtah) {
    return item.id;
  }

  registerChangeInEtatCodes() {
    this.eventSubscriber = this.eventManager.subscribe('etatCodeListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
