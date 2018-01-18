import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonnePtah } from './personne-ptah.model';
import { PersonnePtahPopupService } from './personne-ptah-popup.service';
import { PersonnePtahService } from './personne-ptah.service';

@Component({
    selector: 'jhi-personne-ptah-dialog',
    templateUrl: './personne-ptah-dialog.component.html'
})
export class PersonnePtahDialogComponent implements OnInit {

    personne: PersonnePtah;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private personneService: PersonnePtahService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personneService.update(this.personne));
        } else {
            this.subscribeToSaveResponse(
                this.personneService.create(this.personne));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonnePtah>) {
        result.subscribe((res: PersonnePtah) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PersonnePtah) {
        this.eventManager.broadcast({ name: 'personneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-personne-ptah-popup',
    template: ''
})
export class PersonnePtahPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personnePopupService: PersonnePtahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.personnePopupService
                    .open(PersonnePtahDialogComponent as Component, params['id']);
            } else {
                this.personnePopupService
                    .open(PersonnePtahDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
