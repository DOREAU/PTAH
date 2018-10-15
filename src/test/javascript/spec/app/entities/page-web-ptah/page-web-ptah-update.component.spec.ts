/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { PageWebPtahUpdateComponent } from 'app/entities/page-web-ptah/page-web-ptah-update.component';
import { PageWebPtahService } from 'app/entities/page-web-ptah/page-web-ptah.service';
import { PageWebPtah } from 'app/shared/model/page-web-ptah.model';

describe('Component Tests', () => {
  describe('PageWebPtah Management Update Component', () => {
    let comp: PageWebPtahUpdateComponent;
    let fixture: ComponentFixture<PageWebPtahUpdateComponent>;
    let service: PageWebPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [PageWebPtahUpdateComponent]
      })
        .overrideTemplate(PageWebPtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageWebPtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageWebPtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new PageWebPtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.pageWeb = entity;
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
          const entity = new PageWebPtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.pageWeb = entity;
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
