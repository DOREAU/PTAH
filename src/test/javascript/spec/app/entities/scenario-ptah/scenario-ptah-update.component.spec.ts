/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { ScenarioPtahUpdateComponent } from 'app/entities/scenario-ptah/scenario-ptah-update.component';
import { ScenarioPtahService } from 'app/entities/scenario-ptah/scenario-ptah.service';
import { ScenarioPtah } from 'app/shared/model/scenario-ptah.model';

describe('Component Tests', () => {
  describe('ScenarioPtah Management Update Component', () => {
    let comp: ScenarioPtahUpdateComponent;
    let fixture: ComponentFixture<ScenarioPtahUpdateComponent>;
    let service: ScenarioPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [ScenarioPtahUpdateComponent]
      })
        .overrideTemplate(ScenarioPtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScenarioPtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScenarioPtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ScenarioPtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.scenario = entity;
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
          const entity = new ScenarioPtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.scenario = entity;
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
