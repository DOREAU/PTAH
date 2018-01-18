/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { PtahTestModule } from '../../../test.module';
import { ApplicationPtahComponent } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.component';
import { ApplicationPtahService } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.service';
import { ApplicationPtah } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.model';

describe('Component Tests', () => {

    describe('ApplicationPtah Management Component', () => {
        let comp: ApplicationPtahComponent;
        let fixture: ComponentFixture<ApplicationPtahComponent>;
        let service: ApplicationPtahService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [ApplicationPtahComponent],
                providers: [
                    ApplicationPtahService
                ]
            })
            .overrideTemplate(ApplicationPtahComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApplicationPtahComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationPtahService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ApplicationPtah(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.applications[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
