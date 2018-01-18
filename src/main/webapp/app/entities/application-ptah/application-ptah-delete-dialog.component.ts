import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ApplicationPtah } from './application-ptah.model';
import { ApplicationPtahPopupService } from './application-ptah-popup.service';
import { ApplicationPtahService } from './application-ptah.service';

@Component({
    selector: 'jhi-application-ptah-delete-dialog',
    templateUrl: './application-ptah-delete-dialog.component.html'
})
export class ApplicationPtahDeleteDialogComponent {

    application: ApplicationPtah;

    constructor(
        private applicationService: ApplicationPtahService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'applicationListModification',
                content: 'Deleted an application'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-application-ptah-delete-popup',
    template: ''
})
export class ApplicationPtahDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private applicationPopupService: ApplicationPtahPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.applicationPopupService
                .open(ApplicationPtahDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
