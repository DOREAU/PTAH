/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { EtatCodePtahDeleteDialogComponent } from 'app/entities/etat-code-ptah/etat-code-ptah-delete-dialog.component';
import { EtatCodePtahService } from 'app/entities/etat-code-ptah/etat-code-ptah.service';

describe('Component Tests', () => {
  describe('EtatCodePtah Management Delete Component', () => {
    let comp: EtatCodePtahDeleteDialogComponent;
    let fixture: ComponentFixture<EtatCodePtahDeleteDialogComponent>;
    let service: EtatCodePtahService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EtatCodePtahDeleteDialogComponent]
      })
        .overrideTemplate(EtatCodePtahDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatCodePtahDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatCodePtahService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
