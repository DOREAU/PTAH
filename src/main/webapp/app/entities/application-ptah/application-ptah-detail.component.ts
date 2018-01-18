import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ApplicationPtah } from './application-ptah.model';
import { ApplicationPtahService } from './application-ptah.service';

@Component({
    selector: 'jhi-application-ptah-detail',
    templateUrl: './application-ptah-detail.component.html'
})
export class ApplicationPtahDetailComponent implements OnInit, OnDestroy {

    application: ApplicationPtah;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private applicationService: ApplicationPtahService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApplications();
    }

    load(id) {
        this.applicationService.find(id).subscribe((application) => {
            this.application = application;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApplications() {
        this.eventSubscriber = this.eventManager.subscribe(
            'applicationListModification',
            (response) => this.load(this.application.id)
        );
    }
}
