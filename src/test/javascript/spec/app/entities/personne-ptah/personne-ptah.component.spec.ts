/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PtahTestModule } from '../../../test.module';
import { PersonnePtahComponent } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.component';
import { PersonnePtahService } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.service';
import { PersonnePtah } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.model';

describe('Component Tests', () => {

    describe('PersonnePtah Management Component', () => {
        let comp: PersonnePtahComponent;
        let fixture: ComponentFixture<PersonnePtahComponent>;
        let service: PersonnePtahService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [PersonnePtahComponent],
                providers: [
                    PersonnePtahService
                ]
            })
            .overrideTemplate(PersonnePtahComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonnePtahComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonnePtahService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new PersonnePtah(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.personnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
