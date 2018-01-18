/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PtahTestModule } from '../../../test.module';
import { PersonnePtahDetailComponent } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah-detail.component';
import { PersonnePtahService } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.service';
import { PersonnePtah } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.model';

describe('Component Tests', () => {

    describe('PersonnePtah Management Detail Component', () => {
        let comp: PersonnePtahDetailComponent;
        let fixture: ComponentFixture<PersonnePtahDetailComponent>;
        let service: PersonnePtahService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [PersonnePtahDetailComponent],
                providers: [
                    PersonnePtahService
                ]
            })
            .overrideTemplate(PersonnePtahDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonnePtahDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonnePtahService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new PersonnePtah(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.personne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
