import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';
import { ScenarioPtahService } from './scenario-ptah.service';

@Component({
  selector: 'jhi-scenario-ptah-delete-dialog',
  templateUrl: './scenario-ptah-delete-dialog.component.html'
})
export class ScenarioPtahDeleteDialogComponent {
  scenario: IScenarioPtah;

  constructor(private scenarioService: ScenarioPtahService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.scenarioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'scenarioListModification',
        content: 'Deleted an scenario'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-scenario-ptah-delete-popup',
  template: ''
})
export class ScenarioPtahDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ scenario }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ScenarioPtahDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.scenario = scenario;
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
