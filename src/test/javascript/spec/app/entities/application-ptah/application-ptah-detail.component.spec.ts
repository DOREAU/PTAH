/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { PtahTestModule } from '../../../test.module';
import { ApplicationPtahDetailComponent } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah-detail.component';
import { ApplicationPtahService } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.service';
import { ApplicationPtah } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.model';

describe('Component Tests', () => {

    describe('ApplicationPtah Management Detail Component', () => {
        let comp: ApplicationPtahDetailComponent;
        let fixture: ComponentFixture<ApplicationPtahDetailComponent>;
        let service: ApplicationPtahService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [ApplicationPtahDetailComponent],
                providers: [
                    ApplicationPtahService
                ]
            })
            .overrideTemplate(ApplicationPtahDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApplicationPtahDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationPtahService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ApplicationPtah(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.application).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
