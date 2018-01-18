import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PersonnePtah } from './personne-ptah.model';
import { PersonnePtahService } from './personne-ptah.service';

@Component({
    selector: 'jhi-personne-ptah-detail',
    templateUrl: './personne-ptah-detail.component.html'
})
export class PersonnePtahDetailComponent implements OnInit, OnDestroy {

    personne: PersonnePtah;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private personneService: PersonnePtahService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonnes();
    }

    load(id) {
        this.personneService.find(id).subscribe((personne) => {
            this.personne = personne;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personneListModification',
            (response) => this.load(this.personne.id)
        );
    }
}
