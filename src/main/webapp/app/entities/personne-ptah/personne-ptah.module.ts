import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from '../../shared';
import {
    PersonnePtahService,
    PersonnePtahPopupService,
    PersonnePtahComponent,
    PersonnePtahDetailComponent,
    PersonnePtahDialogComponent,
    PersonnePtahPopupComponent,
    PersonnePtahDeletePopupComponent,
    PersonnePtahDeleteDialogComponent,
    personneRoute,
    personnePopupRoute,
} from './';

const ENTITY_STATES = [
    ...personneRoute,
    ...personnePopupRoute,
];

@NgModule({
    imports: [
        PtahSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PersonnePtahComponent,
        PersonnePtahDetailComponent,
        PersonnePtahDialogComponent,
        PersonnePtahDeleteDialogComponent,
        PersonnePtahPopupComponent,
        PersonnePtahDeletePopupComponent,
    ],
    entryComponents: [
        PersonnePtahComponent,
        PersonnePtahDialogComponent,
        PersonnePtahPopupComponent,
        PersonnePtahDeleteDialogComponent,
        PersonnePtahDeletePopupComponent,
    ],
    providers: [
        PersonnePtahService,
        PersonnePtahPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahPersonnePtahModule {}
