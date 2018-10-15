import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';
import { Principal } from 'app/core';
import { LienPageWebPtahService } from './lien-page-web-ptah.service';

@Component({
  selector: 'jhi-lien-page-web-ptah',
  templateUrl: './lien-page-web-ptah.component.html'
})
export class LienPageWebPtahComponent implements OnInit, OnDestroy {
  lienPageWebs: ILienPageWebPtah[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private lienPageWebService: LienPageWebPtahService,
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
      this.lienPageWebService
        .search({
          query: this.currentSearch
        })
        .subscribe(
          (res: HttpResponse<ILienPageWebPtah[]>) => (this.lienPageWebs = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.lienPageWebService.query().subscribe(
      (res: HttpResponse<ILienPageWebPtah[]>) => {
        this.lienPageWebs = res.body;
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
    this.registerChangeInLienPageWebs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILienPageWebPtah) {
    return item.id;
  }

  registerChangeInLienPageWebs() {
    this.eventSubscriber = this.eventManager.subscribe('lienPageWebListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
