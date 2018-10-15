/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { CodeAccesPtahUpdateComponent } from 'app/entities/code-acces-ptah/code-acces-ptah-update.component';
import { CodeAccesPtahService } from 'app/entities/code-acces-ptah/code-acces-ptah.service';
import { CodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';

describe('Component Tests', () => {
  describe('CodeAccesPtah Management Update Component', () => {
    let comp: CodeAccesPtahUpdateComponent;
    let fixture: ComponentFixture<CodeAccesPtahUpdateComponent>;
    let service: CodeAccesPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [CodeAccesPtahUpdateComponent]
      })
        .overrideTemplate(CodeAccesPtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodeAccesPtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodeAccesPtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new CodeAccesPtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.codeAcces = entity;
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
          const entity = new CodeAccesPtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.codeAcces = entity;
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
