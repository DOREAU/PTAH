import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  LienPageWebPtahComponent,
  LienPageWebPtahDetailComponent,
  LienPageWebPtahUpdateComponent,
  LienPageWebPtahDeletePopupComponent,
  LienPageWebPtahDeleteDialogComponent,
  lienPageWebRoute,
  lienPageWebPopupRoute
} from './';

const ENTITY_STATES = [...lienPageWebRoute, ...lienPageWebPopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LienPageWebPtahComponent,
    LienPageWebPtahDetailComponent,
    LienPageWebPtahUpdateComponent,
    LienPageWebPtahDeleteDialogComponent,
    LienPageWebPtahDeletePopupComponent
  ],
  entryComponents: [
    LienPageWebPtahComponent,
    LienPageWebPtahUpdateComponent,
    LienPageWebPtahDeleteDialogComponent,
    LienPageWebPtahDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahLienPageWebPtahModule {}
