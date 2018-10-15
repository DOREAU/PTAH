import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';
import { Principal } from 'app/core';
import { PageWebPtahService } from './page-web-ptah.service';

@Component({
  selector: 'jhi-page-web-ptah',
  templateUrl: './page-web-ptah.component.html'
})
export class PageWebPtahComponent implements OnInit, OnDestroy {
  pageWebs: IPageWebPtah[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private pageWebService: PageWebPtahService,
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
      this.pageWebService
        .search({
          query: this.currentSearch
        })
        .subscribe(
          (res: HttpResponse<IPageWebPtah[]>) => (this.pageWebs = res.body),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.pageWebService.query().subscribe(
      (res: HttpResponse<IPageWebPtah[]>) => {
        this.pageWebs = res.body;
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
    this.registerChangeInPageWebs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPageWebPtah) {
    return item.id;
  }

  registerChangeInPageWebs() {
    this.eventSubscriber = this.eventManager.subscribe('pageWebListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
