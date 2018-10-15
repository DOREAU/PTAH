/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PtahTestModule } from '../../../test.module';
import { LienPageWebPtahComponent } from 'app/entities/lien-page-web-ptah/lien-page-web-ptah.component';
import { LienPageWebPtahService } from 'app/entities/lien-page-web-ptah/lien-page-web-ptah.service';
import { LienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

describe('Component Tests', () => {
  describe('LienPageWebPtah Management Component', () => {
    let comp: LienPageWebPtahComponent;
    let fixture: ComponentFixture<LienPageWebPtahComponent>;
    let service: LienPageWebPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [LienPageWebPtahComponent],
        providers: []
      })
        .overrideTemplate(LienPageWebPtahComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LienPageWebPtahComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LienPageWebPtahService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LienPageWebPtah(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lienPageWebs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
