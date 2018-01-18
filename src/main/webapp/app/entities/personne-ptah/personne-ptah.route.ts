import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PersonnePtahComponent } from './personne-ptah.component';
import { PersonnePtahDetailComponent } from './personne-ptah-detail.component';
import { PersonnePtahPopupComponent } from './personne-ptah-dialog.component';
import { PersonnePtahDeletePopupComponent } from './personne-ptah-delete-dialog.component';

export const personneRoute: Routes = [
    {
        path: 'personne-ptah',
        component: PersonnePtahComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personne-ptah/:id',
        component: PersonnePtahDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personnePopupRoute: Routes = [
    {
        path: 'personne-ptah-new',
        component: PersonnePtahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personne-ptah/:id/edit',
        component: PersonnePtahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personne-ptah/:id/delete',
        component: PersonnePtahDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Personnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
