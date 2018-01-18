import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PtahApplicationPtahModule } from './application-ptah/application-ptah.module';
import { PtahPersonnePtahModule } from './personne-ptah/personne-ptah.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PtahApplicationPtahModule,
        PtahPersonnePtahModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PtahEntityModule {}
