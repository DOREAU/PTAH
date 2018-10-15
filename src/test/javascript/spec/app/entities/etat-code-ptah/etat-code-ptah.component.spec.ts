/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PtahTestModule } from '../../../test.module';
import { EtatCodePtahComponent } from 'app/entities/etat-code-ptah/etat-code-ptah.component';
import { EtatCodePtahService } from 'app/entities/etat-code-ptah/etat-code-ptah.service';
import { EtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

describe('Component Tests', () => {
  describe('EtatCodePtah Management Component', () => {
    let comp: EtatCodePtahComponent;
    let fixture: ComponentFixture<EtatCodePtahComponent>;
    let service: EtatCodePtahService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PtahTestModule],
        declarations: [EtatCodePtahComponent],
        providers: []
      })
        .overrideTemplate(EtatCodePtahComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatCodePtahComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatCodePtahService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EtatCodePtah(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.etatCodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
