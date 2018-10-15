/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PtahTestModule } from '../../../test.module';
import { PageWebPtahComponent } from 'app/entities/page-web-ptah/page-web-ptah.component';
import { PageWebPtahService } from 'app/entities/page-web-ptah/page-web-ptah.service';
import { PageWebPtah } from 'app/shared/model/page-web-ptah.model';

describe('Component Tests', () => {
  describe('PageWebPtah Management Component', () => {
    let comp: PageWebPtahComponent;
    let fixture: ComponentFixture<PageWebPtahComponent>;
    let service: PageWebPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [PageWebPtahComponent],
        providers: []
      })
        .overrideTemplate(PageWebPtahComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageWebPtahComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageWebPtahService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PageWebPtah(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pageWebs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
