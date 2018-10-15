/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PtahTestModule } from '../../../test.module';
import { EquipePtahComponent } from 'app/entities/equipe-ptah/equipe-ptah.component';
import { EquipePtahService } from 'app/entities/equipe-ptah/equipe-ptah.service';
import { EquipePtah } from 'app/shared/model/equipe-ptah.model';

describe('Component Tests', () => {
  describe('EquipePtah Management Component', () => {
    let comp: EquipePtahComponent;
    let fixture: ComponentFixture<EquipePtahComponent>;
    let service: EquipePtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EquipePtahComponent],
        providers: []
      })
        .overrideTemplate(EquipePtahComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipePtahComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipePtahService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EquipePtah(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.equipes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
