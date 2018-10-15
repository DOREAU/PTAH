/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { EquipePtahDeleteDialogComponent } from 'app/entities/equipe-ptah/equipe-ptah-delete-dialog.component';
import { EquipePtahService } from 'app/entities/equipe-ptah/equipe-ptah.service';

describe('Component Tests', () => {
  describe('EquipePtah Management Delete Component', () => {
    let comp: EquipePtahDeleteDialogComponent;
    let fixture: ComponentFixture<EquipePtahDeleteDialogComponent>;
    let service: EquipePtahService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EquipePtahDeleteDialogComponent]
      })
        .overrideTemplate(EquipePtahDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipePtahDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipePtahService);
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
