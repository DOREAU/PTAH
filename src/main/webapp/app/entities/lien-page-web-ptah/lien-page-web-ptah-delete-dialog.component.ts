import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';
import { LienPageWebPtahService } from './lien-page-web-ptah.service';

@Component({
  selector: 'jhi-lien-page-web-ptah-delete-dialog',
  templateUrl: './lien-page-web-ptah-delete-dialog.component.html'
})
export class LienPageWebPtahDeleteDialogComponent {
  lienPageWeb: ILienPageWebPtah;

  constructor(
    private lienPageWebService: LienPageWebPtahService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.lienPageWebService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'lienPageWebListModification',
        content: 'Deleted an lienPageWeb'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-lien-page-web-ptah-delete-popup',
  template: ''
})
export class LienPageWebPtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lienPageWeb }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LienPageWebPtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.lienPageWeb = lienPageWeb;
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
