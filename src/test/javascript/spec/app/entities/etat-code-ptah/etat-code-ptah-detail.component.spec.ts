/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { EtatCodePtahDetailComponent } from 'app/entities/etat-code-ptah/etat-code-ptah-detail.component';
import { EtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

describe('Component Tests', () => {
  describe('EtatCodePtah Management Detail Component', () => {
    let comp: EtatCodePtahDetailComponent;
    let fixture: ComponentFixture<EtatCodePtahDetailComponent>;
    const route = ({ data: of({ etatCode: new EtatCodePtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EtatCodePtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EtatCodePtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtatCodePtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etatCode).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
