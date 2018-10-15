import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  EtatCodePtahComponent,
  EtatCodePtahDetailComponent,
  EtatCodePtahUpdateComponent,
  EtatCodePtahDeletePopupComponent,
  EtatCodePtahDeleteDialogComponent,
  etatCodeRoute,
  etatCodePopupRoute
} from './';

const ENTITY_STATES = [...etatCodeRoute, ...etatCodePopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EtatCodePtahComponent,
    EtatCodePtahDetailComponent,
    EtatCodePtahUpdateComponent,
    EtatCodePtahDeleteDialogComponent,
    EtatCodePtahDeletePopupComponent
  ],
  entryComponents: [
    EtatCodePtahComponent,
    EtatCodePtahUpdateComponent,
    EtatCodePtahDeleteDialogComponent,
    EtatCodePtahDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahEtatCodePtahModule {}
