import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PtahSharedModule } from 'app/shared';
import {
  ScenarioPtahComponent,
  ScenarioPtahDetailComponent,
  ScenarioPtahUpdateComponent,
  ScenarioPtahDeletePopupComponent,
  ScenarioPtahDeleteDialogComponent,
  scenarioRoute,
  scenarioPopupRoute
} from './';

const ENTITY_STATES = [...scenarioRoute, ...scenarioPopupRoute];

@NgModule({
  imports: [PtahSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ScenarioPtahComponent,
    ScenarioPtahDetailComponent,
    ScenarioPtahUpdateComponent,
    ScenarioPtahDeleteDialogComponent,
    ScenarioPtahDeletePopupComponent
  ],
  entryComponents: [
    ScenarioPtahComponent,
    ScenarioPtahUpdateComponent,
    ScenarioPtahDeleteDialogComponent,
    ScenarioPtahDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahScenarioPtahModule {}
