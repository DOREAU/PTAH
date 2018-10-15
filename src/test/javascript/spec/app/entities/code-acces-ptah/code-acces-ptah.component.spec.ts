/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PtahTestModule } from '../../../test.module';
import { CodeAccesPtahComponent } from 'app/entities/code-acces-ptah/code-acces-ptah.component';
import { CodeAccesPtahService } from 'app/entities/code-acces-ptah/code-acces-ptah.service';
import { CodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';

describe('Component Tests', () => {
  describe('CodeAccesPtah Management Component', () => {
    let comp: CodeAccesPtahComponent;
    let fixture: ComponentFixture<CodeAccesPtahComponent>;
    let service: CodeAccesPtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [CodeAccesPtahComponent],
        providers: []
      })
        .overrideTemplate(CodeAccesPtahComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodeAccesPtahComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodeAccesPtahService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CodeAccesPtah(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.codeAcces[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
