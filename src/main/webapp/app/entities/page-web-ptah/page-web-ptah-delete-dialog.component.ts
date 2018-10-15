import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';
import { PageWebPtahService } from './page-web-ptah.service';

@Component({
  selector: 'jhi-page-web-ptah-delete-dialog',
  templateUrl: './page-web-ptah-delete-dialog.component.html'
})
export class PageWebPtahDeleteDialogComponent {
  pageWeb: IPageWebPtah;

  constructor(private pageWebService: PageWebPtahService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pageWebService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pageWebListModification',
        content: 'Deleted an pageWeb'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-page-web-ptah-delete-popup',
  template: ''
})
export class PageWebPtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pageWeb }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PageWebPtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pageWeb = pageWeb;
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
