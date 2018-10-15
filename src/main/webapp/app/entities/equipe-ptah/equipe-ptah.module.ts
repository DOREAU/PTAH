import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  EquipePtahComponent,
  EquipePtahDetailComponent,
  EquipePtahUpdateComponent,
  EquipePtahDeletePopupComponent,
  EquipePtahDeleteDialogComponent,
  equipeRoute,
  equipePopupRoute
} from './';

const ENTITY_STATES = [...equipeRoute, ...equipePopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EquipePtahComponent,
    EquipePtahDetailComponent,
    EquipePtahUpdateComponent,
    EquipePtahDeleteDialogComponent,
    EquipePtahDeletePopupComponent
  ],
  entryComponents: [EquipePtahComponent, EquipePtahUpdateComponent, EquipePtahDeleteDialogComponent, EquipePtahDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahEquipePtahModule {}
