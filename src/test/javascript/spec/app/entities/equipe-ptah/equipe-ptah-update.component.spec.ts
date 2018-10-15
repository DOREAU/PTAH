/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { EquipePtahUpdateComponent } from 'app/entities/equipe-ptah/equipe-ptah-update.component';
import { EquipePtahService } from 'app/entities/equipe-ptah/equipe-ptah.service';
import { EquipePtah } from 'app/shared/model/equipe-ptah.model';

describe('Component Tests', () => {
  describe('EquipePtah Management Update Component', () => {
    let comp: EquipePtahUpdateComponent;
    let fixture: ComponentFixture<EquipePtahUpdateComponent>;
    let service: EquipePtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EquipePtahUpdateComponent]
      })
        .overrideTemplate(EquipePtahUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipePtahUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipePtahService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new EquipePtah(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.equipe = entity;
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
          const entity = new EquipePtah();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.equipe = entity;
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
