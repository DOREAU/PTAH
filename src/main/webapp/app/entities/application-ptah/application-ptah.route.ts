import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApplicationPtahComponent } from './application-ptah.component';
import { ApplicationPtahDetailComponent } from './application-ptah-detail.component';
import { ApplicationPtahPopupComponent } from './application-ptah-dialog.component';
import { ApplicationPtahDeletePopupComponent } from './application-ptah-delete-dialog.component';

export const applicationRoute: Routes = [
    {
        path: 'application-ptah',
        component: ApplicationPtahComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'application-ptah/:id',
        component: ApplicationPtahDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationPopupRoute: Routes = [
    {
        path: 'application-ptah-new',
        component: ApplicationPtahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application-ptah/:id/edit',
        component: ApplicationPtahPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application-ptah/:id/delete',
        component: ApplicationPtahDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applications'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
