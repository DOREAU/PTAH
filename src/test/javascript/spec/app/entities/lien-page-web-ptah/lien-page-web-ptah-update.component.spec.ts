/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { LienPageWebPtahUpdateComponent } from 'app/entities/lien-page-web-ptah/lien-page-web-ptah-update.component';
import { LienPageWebPtahService } from 'app/entities/lien-page-web-ptah/lien-page-web-ptah.service';
import { LienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

describe('Component Tests', () => {
  describe('LienPageWebPtah Management Update Component', () => {
    let comp: LienPageWebPtahUpdateComponent;
    let fixture: ComponentFixture<LienPageWebPtahUpdateComponent>;
    let service: LienPageWebPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [LienPageWebPtahUpdateComponent]
      })
        .overrideTemplate(LienPageWebPtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LienPageWebPtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LienPageWebPtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new LienPageWebPtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.lienPageWeb = entity;
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
          const entity = new LienPageWebPtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.lienPageWeb = entity;
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
