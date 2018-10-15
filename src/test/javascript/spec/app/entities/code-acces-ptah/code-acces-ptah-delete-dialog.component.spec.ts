/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { CodeAccesPtahDeleteDialogComponent } from 'app/entities/code-acces-ptah/code-acces-ptah-delete-dialog.component';
import { CodeAccesPtahService } from 'app/entities/code-acces-ptah/code-acces-ptah.service';

describe('Component Tests', () => {
  describe('CodeAccesPtah Management Delete Component', () => {
    let comp: CodeAccesPtahDeleteDialogComponent;
    let fixture: ComponentFixture<CodeAccesPtahDeleteDialogComponent>;
    let service: CodeAccesPtahService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [CodeAccesPtahDeleteDialogComponent]
      })
        .overrideTemplate(CodeAccesPtahDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodeAccesPtahDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodeAccesPtahService);
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
