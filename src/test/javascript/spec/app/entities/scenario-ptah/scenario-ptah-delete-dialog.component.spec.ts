/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { ScenarioPtahDeleteDialogComponent } from 'app/entities/scenario-ptah/scenario-ptah-delete-dialog.component';
import { ScenarioPtahService } from 'app/entities/scenario-ptah/scenario-ptah.service';

describe('Component Tests', () => {
  describe('ScenarioPtah Management Delete Component', () => {
    let comp: ScenarioPtahDeleteDialogComponent;
    let fixture: ComponentFixture<ScenarioPtahDeleteDialogComponent>;
    let service: ScenarioPtahService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [ScenarioPtahDeleteDialogComponent]
      })
        .overrideTemplate(ScenarioPtahDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScenarioPtahDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScenarioPtahService);
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
