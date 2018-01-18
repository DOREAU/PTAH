import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from '../../shared';
import {
    ApplicationPtahService,
    ApplicationPtahPopupService,
    ApplicationPtahComponent,
    ApplicationPtahDetailComponent,
    ApplicationPtahDialogComponent,
    ApplicationPtahPopupComponent,
    ApplicationPtahDeletePopupComponent,
    ApplicationPtahDeleteDialogComponent,
    applicationRoute,
    applicationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...applicationRoute,
    ...applicationPopupRoute,
];

@NgModule({
    imports: [
        PtahSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApplicationPtahComponent,
        ApplicationPtahDetailComponent,
        ApplicationPtahDialogComponent,
        ApplicationPtahDeleteDialogComponent,
        ApplicationPtahPopupComponent,
        ApplicationPtahDeletePopupComponent,
    ],
    entryComponents: [
        ApplicationPtahComponent,
        ApplicationPtahDialogComponent,
        ApplicationPtahPopupComponent,
        ApplicationPtahDeleteDialogComponent,
        ApplicationPtahDeletePopupComponent,
    ],
    providers: [
        ApplicationPtahService,
        ApplicationPtahPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahApplicationPtahModule {}
