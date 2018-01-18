/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { ApplicationPtahDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah-delete-dialog.component';
import { ApplicationPtahService } from '../../../../../../main/webapp/app/entities/application-ptah/application-ptah.service';

describe('Component Tests', () => {

    describe('ApplicationPtah Management Delete Component', () => {
        let comp: ApplicationPtahDeleteDialogComponent;
        let fixture: ComponentFixture<ApplicationPtahDeleteDialogComponent>;
        let service: ApplicationPtahService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [ApplicationPtahDeleteDialogComponent],
                providers: [
                    ApplicationPtahService
                ]
            })
            .overrideTemplate(ApplicationPtahDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApplicationPtahDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationPtahService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
