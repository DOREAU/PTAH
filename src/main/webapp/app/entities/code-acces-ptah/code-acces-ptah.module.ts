import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  CodeAccesPtahComponent,
  CodeAccesPtahDetailComponent,
  CodeAccesPtahUpdateComponent,
  CodeAccesPtahDeletePopupComponent,
  CodeAccesPtahDeleteDialogComponent,
  codeAccesRoute,
  codeAccesPopupRoute
} from './';

const ENTITY_STATES = [...codeAccesRoute, ...codeAccesPopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CodeAccesPtahComponent,
    CodeAccesPtahDetailComponent,
    CodeAccesPtahUpdateComponent,
    CodeAccesPtahDeleteDialogComponent,
    CodeAccesPtahDeletePopupComponent
  ],
  entryComponents: [
    CodeAccesPtahComponent,
    CodeAccesPtahUpdateComponent,
    CodeAccesPtahDeleteDialogComponent,
    CodeAccesPtahDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahCodeAccesPtahModule {}
