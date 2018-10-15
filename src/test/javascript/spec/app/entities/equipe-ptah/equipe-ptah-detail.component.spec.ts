/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { EquipePtahDetailComponent } from 'app/entities/equipe-ptah/equipe-ptah-detail.component';
import { EquipePtah } from 'app/shared/model/equipe-ptah.model';

describe('Component Tests', () => {
  describe('EquipePtah Management Detail Component', () => {
    let comp: EquipePtahDetailComponent;
    let fixture: ComponentFixture<EquipePtahDetailComponent>;
    const route = ({ data: of({ equipe: new EquipePtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EquipePtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EquipePtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipePtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equipe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
