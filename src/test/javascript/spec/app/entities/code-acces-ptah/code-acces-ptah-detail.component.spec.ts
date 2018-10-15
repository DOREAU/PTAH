/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PtahTestModule } from '../../../test.module';
import { CodeAccesPtahDetailComponent } from 'app/entities/code-acces-ptah/code-acces-ptah-detail.component';
import { CodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';

describe('Component Tests', () => {
  describe('CodeAccesPtah Management Detail Component', () => {
    let comp: CodeAccesPtahDetailComponent;
    let fixture: ComponentFixture<CodeAccesPtahDetailComponent>;
    const route = ({ data: of({ codeAcces: new CodeAccesPtah(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [CodeAccesPtahDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CodeAccesPtahDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodeAccesPtahDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.codeAcces).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
