import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PtahScenarioPtahModule } from './scenario-ptah/scenario-ptah.module';
import { PtahPageWebPtahModule } from './page-web-ptah/page-web-ptah.module';
import { PtahLienPageWebPtahModule } from './lien-page-web-ptah/lien-page-web-ptah.module';
import { PtahCodeAccesPtahModule } from './code-acces-ptah/code-acces-ptah.module';
import { PtahEtatCodePtahModule } from './etat-code-ptah/etat-code-ptah.module';
import { PtahEquipePtahModule } from './equipe-ptah/equipe-ptah.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
  imports: [
    PtahScenarioPtahModule,
    PtahPageWebPtahModule,
    PtahLienPageWebPtahModule,
    PtahCodeAccesPtahModule,
    PtahEtatCodePtahModule,
    PtahEquipePtahModule
    /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahEntityModule {}
