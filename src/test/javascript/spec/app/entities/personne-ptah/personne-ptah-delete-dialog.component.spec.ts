/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { PersonnePtahDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah-delete-dialog.component';
import { PersonnePtahService } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.service';

describe('Component Tests', () => {

    describe('PersonnePtah Management Delete Component', () => {
        let comp: PersonnePtahDeleteDialogComponent;
        let fixture: ComponentFixture<PersonnePtahDeleteDialogComponent>;
        let service: PersonnePtahService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [PersonnePtahDeleteDialogComponent],
                providers: [
                    PersonnePtahService
                ]
            })
            .overrideTemplate(PersonnePtahDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonnePtahDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonnePtahService);
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
