import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ApplicationPtah } from './application-ptah.model';
import { ApplicationPtahPopupService } from './application-ptah-popup.service';
import { ApplicationPtahService } from './application-ptah.service';
import { PersonnePtah, PersonnePtahService } from '../personne-ptah';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-application-ptah-dialog',
    templateUrl: './application-ptah-dialog.component.html'
})
export class ApplicationPtahDialogComponent implements OnInit {

    application: ApplicationPtah;
    isSaving: boolean;

    personnes: PersonnePtah[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private applicationService: ApplicationPtahService,
        private personneService: PersonnePtahService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personneService.query()
            .subscribe((res: ResponseWrapper) => { this.personnes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.application.id !== undefined) {
            this.subscribeToSaveResponse(
                this.applicationService.update(this.application));
        } else {
            this.subscribeToSaveResponse(
                this.applicationService.create(this.application));
        }
    }

    private subscribeToSaveResponse(result: Observable<ApplicationPtah>) {
        result.subscribe((res: ApplicationPtah) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ApplicationPtah) {
        this.eventManager.broadcast({ name: 'applicationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonneById(index: number, item: PersonnePtah) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-application-ptah-popup',
    template: ''
})
export class ApplicationPtahPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationPopupService: ApplicationPtahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.applicationPopupService
                    .open(ApplicationPtahDialogComponent as Component, params['id']);
            } else {
                this.applicationPopupService
                    .open(ApplicationPtahDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
