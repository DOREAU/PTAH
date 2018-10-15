import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PageWebPtah } from 'app/shared/model/page-web-ptah.model';
import { PageWebPtahService } from './page-web-ptah.service';
import { PageWebPtahComponent } from './page-web-ptah.component';
import { PageWebPtahDetailComponent } from './page-web-ptah-detail.component';
import { PageWebPtahUpdateComponent } from './page-web-ptah-update.component';
import { PageWebPtahDeletePopupComponent } from './page-web-ptah-delete-dialog.component';
import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';

@Injectable({ providedIn: 'root' })
export class PageWebPtahResolve implements Resolve<IPageWebPtah> {
  constructor(private service: PageWebPtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((pageWeb: HttpResponse<PageWebPtah>) => pageWeb.body));
    }
    return of(new PageWebPtah());
  }
}

export const pageWebRoute: Routes = [
  {
    path: 'page-web-ptah',
    component: PageWebPtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'page-web-ptah/:id/view',
    component: PageWebPtahDetailComponent,
    resolve: {
      pageWeb: PageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'page-web-ptah/new',
    component: PageWebPtahUpdateComponent,
    resolve: {
      pageWeb: PageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageWebs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'page-web-ptah/:id/edit',
    component: PageWebPtahUpdateComponent,
    resolve: {
      pageWeb: PageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageWebs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pageWebPopupRoute: Routes = [
  {
    path: 'page-web-ptah/:id/delete',
    component: PageWebPtahDeletePopupComponent,
    resolve: {
      pageWeb: PageWebPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'PageWebs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
