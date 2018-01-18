/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { PtahTestModule } from '../../../test.module';
import { PersonnePtahDialogComponent } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah-dialog.component';
import { PersonnePtahService } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.service';
import { PersonnePtah } from '../../../../../../main/webapp/app/entities/personne-ptah/personne-ptah.model';

describe('Component Tests', () => {

    describe('PersonnePtah Management Dialog Component', () => {
        let comp: PersonnePtahDialogComponent;
        let fixture: ComponentFixture<PersonnePtahDialogComponent>;
        let service: PersonnePtahService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PtahTestModule],
                declarations: [PersonnePtahDialogComponent],
                providers: [
                    PersonnePtahService
                ]
            })
            .overrideTemplate(PersonnePtahDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonnePtahDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonnePtahService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PersonnePtah(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.personne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PersonnePtah();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.personne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'personneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
