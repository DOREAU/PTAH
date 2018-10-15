/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { ScenarioPtahDetailComponent } from 'app/entities/scenario-ptah/scenario-ptah-detail.component';
import { ScenarioPtah } from 'app/shared/model/scenario-ptah.model';

describe('Component Tests', () => {
  describe('ScenarioPtah Management Detail Component', () => {
    let comp: ScenarioPtahDetailComponent;
    let fixture: ComponentFixture<ScenarioPtahDetailComponent>;
    const route = ({ data: of({ scenario: new ScenarioPtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [ScenarioPtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScenarioPtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScenarioPtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scenario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
