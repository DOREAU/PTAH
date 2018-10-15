import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';
import { EtatCodePtahService } from './etat-code-ptah.service';

@Component({
  selector: 'jhi-etat-code-ptah-delete-dialog',
  templateUrl: './etat-code-ptah-delete-dialog.component.html'
})
export class EtatCodePtahDeleteDialogComponent {
  etatCode: IEtatCodePtah;

  constructor(private etatCodeService: EtatCodePtahService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.etatCodeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'etatCodeListModification',
        content: 'Deleted an etatCode'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-etat-code-ptah-delete-popup',
  template: ''
})
export class EtatCodePtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ etatCode }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EtatCodePtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.etatCode = etatCode;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
