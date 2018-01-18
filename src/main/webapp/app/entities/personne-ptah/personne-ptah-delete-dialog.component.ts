import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PersonnePtah } from './personne-ptah.model';
import { PersonnePtahPopupService } from './personne-ptah-popup.service';
import { PersonnePtahService } from './personne-ptah.service';

@Component({
    selector: 'jhi-personne-ptah-delete-dialog',
    templateUrl: './personne-ptah-delete-dialog.component.html'
})
export class PersonnePtahDeleteDialogComponent {

    personne: PersonnePtah;

    constructor(
        private personneService: PersonnePtahService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personneListModification',
                content: 'Deleted an personne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personne-ptah-delete-popup',
    template: ''
})
export class PersonnePtahDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personnePopupService: PersonnePtahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.personnePopupService
                .open(PersonnePtahDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
