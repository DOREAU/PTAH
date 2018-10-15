import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';
import { Principal } from 'app/core';
import { EquipePtahService } from './equipe-ptah.service';

@Component({
  selector: 'jhi-equipe-ptah',
  templateUrl: './equipe-ptah.component.html'
})
export class EquipePtahComponent implements OnInit, OnDestroy {
  equipes: IEquipePtah[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private equipeService: EquipePtahService,
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
      this.equipeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IEquipePtah[]>) => (this.equipes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.equipeService.query().subscribe(
      (res: HttpResponse<IEquipePtah[]>) => {
        this.equipes = res.body;
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
    this.registerChangeInEquipes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEquipePtah) {
    return item.id;
  }

  registerChangeInEquipes() {
    this.eventSubscriber = this.eventManager.subscribe('equipeListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
