/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { EtatCodePtahUpdateComponent } from 'app/entities/etat-code-ptah/etat-code-ptah-update.component';
import { EtatCodePtahService } from 'app/entities/etat-code-ptah/etat-code-ptah.service';
import { EtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

describe('Component Tests', () => {
  describe('EtatCodePtah Management Update Component', () => {
    let comp: EtatCodePtahUpdateComponent;
    let fixture: ComponentFixture<EtatCodePtahUpdateComponent>;
    let service: EtatCodePtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EtatCodePtahUpdateComponent]
      })
        .overrideTemplate(EtatCodePtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatCodePtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatCodePtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new EtatCodePtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.etatCode = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new EtatCodePtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.etatCode = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});
