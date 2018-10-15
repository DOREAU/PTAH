import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  PageWebPtahComponent,
  PageWebPtahDetailComponent,
  PageWebPtahUpdateComponent,
  PageWebPtahDeletePopupComponent,
  PageWebPtahDeleteDialogComponent,
  pageWebRoute,
  pageWebPopupRoute
} from './';

const ENTITY_STATES = [...pageWebRoute, ...pageWebPopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PageWebPtahComponent,
    PageWebPtahDetailComponent,
    PageWebPtahUpdateComponent,
    PageWebPtahDeleteDialogComponent,
    PageWebPtahDeletePopupComponent
  ],
  entryComponents: [PageWebPtahComponent, PageWebPtahUpdateComponent, PageWebPtahDeleteDialogComponent, PageWebPtahDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahPageWebPtahModule {}
