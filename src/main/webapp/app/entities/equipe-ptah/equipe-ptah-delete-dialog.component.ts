import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';
import { EquipePtahService } from './equipe-ptah.service';

@Component({
  selector: 'jhi-equipe-ptah-delete-dialog',
  templateUrl: './equipe-ptah-delete-dialog.component.html'
})
export class EquipePtahDeleteDialogComponent {
  equipe: IEquipePtah;

  constructor(private equipeService: EquipePtahService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.equipeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'equipeListModification',
        content: 'Deleted an equipe'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-equipe-ptah-delete-popup',
  template: ''
})
export class EquipePtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EquipePtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.equipe = equipe;
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
