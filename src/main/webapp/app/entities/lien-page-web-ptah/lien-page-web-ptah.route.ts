import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { LienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';
import { LienPageWebPtahService } from './lien-page-web-ptah.service';
import { LienPageWebPtahComponent } from './lien-page-web-ptah.component';
import { LienPageWebPtahDetailComponent } from './lien-page-web-ptah-detail.component';
import { LienPageWebPtahUpdateComponent } from './lien-page-web-ptah-update.component';
import { LienPageWebPtahDeletePopupComponent } from './lien-page-web-ptah-delete-dialog.component';
import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

@Injectable({ providedIn: 'root' })
export class LienPageWebPtahResolve implements Resolve<ILienPageWebPtah> {
  constructor(private service: LienPageWebPtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((lienPageWeb: HttpResponse<LienPageWebPtah>) => lienPageWeb.body));
    }
    return of(new LienPageWebPtah());
  }
}

export const lienPageWebRoute: Routes = [
  {
    path: 'lien-page-web-ptah',
    component: LienPageWebPtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienPageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'lien-page-web-ptah/:id/view',
    component: LienPageWebPtahDetailComponent,
    resolve: {
      lienPageWeb: LienPageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienPageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'lien-page-web-ptah/new',
    component: LienPageWebPtahUpdateComponent,
    resolve: {
      lienPageWeb: LienPageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienPageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'lien-page-web-ptah/:id/edit',
    component: LienPageWebPtahUpdateComponent,
    resolve: {
      lienPageWeb: LienPageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienPageWebs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lienPageWebPopupRoute: Routes = [
  {
    path: 'lien-page-web-ptah/:id/delete',
    component: LienPageWebPtahDeletePopupComponent,
    resolve: {
      lienPageWeb: LienPageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'LienPageWebs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
