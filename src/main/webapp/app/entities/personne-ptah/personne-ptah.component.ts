import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PersonnePtah } from './personne-ptah.model';
import { PersonnePtahService } from './personne-ptah.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-personne-ptah',
    templateUrl: './personne-ptah.component.html'
})
export class PersonnePtahComponent implements OnInit, OnDestroy {
personnes: PersonnePtah[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private personneService: PersonnePtahService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.personneService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.personnes = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.personneService.query().subscribe(
            (res: ResponseWrapper) => {
                this.personnes = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
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
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPersonnes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PersonnePtah) {
        return item.id;
    }
    registerChangeInPersonnes() {
        this.eventSubscriber = this.eventManager.subscribe('personneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
