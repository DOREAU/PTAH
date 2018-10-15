/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { PageWebPtahDetailComponent } from 'app/entities/page-web-ptah/page-web-ptah-detail.component';
import { PageWebPtah } from 'app/shared/model/page-web-ptah.model';

describe('Component Tests', () => {
  describe('PageWebPtah Management Detail Component', () => {
    let comp: PageWebPtahDetailComponent;
    let fixture: ComponentFixture<PageWebPtahDetailComponent>;
    const route = ({ data: of({ pageWeb: new PageWebPtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [PageWebPtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PageWebPtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PageWebPtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pageWeb).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
