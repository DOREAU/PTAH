import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { CodeAccesPtahService } from './code-acces-ptah.service';

@Component({
  selector: 'jhi-code-acces-ptah-delete-dialog',
  templateUrl: './code-acces-ptah-delete-dialog.component.html'
})
export class CodeAccesPtahDeleteDialogComponent {
  codeAcces: ICodeAccesPtah;

  constructor(private codeAccesService: CodeAccesPtahService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.codeAccesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'codeAccesListModification',
        content: 'Deleted an codeAcces'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-code-acces-ptah-delete-popup',
  template: ''
})
export class CodeAccesPtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ codeAcces }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CodeAccesPtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.codeAcces = codeAcces;
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
