/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { LienPageWebPtahDetailComponent } from 'app/entities/lien-page-web-ptah/lien-page-web-ptah-detail.component';
import { LienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

describe('Component Tests', () => {
  describe('LienPageWebPtah Management Detail Component', () => {
    let comp: LienPageWebPtahDetailComponent;
    let fixture: ComponentFixture<LienPageWebPtahDetailComponent>;
    const route = ({ data: of({ lienPageWeb: new LienPageWebPtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [LienPageWebPtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LienPageWebPtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LienPageWebPtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lienPageWeb).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
