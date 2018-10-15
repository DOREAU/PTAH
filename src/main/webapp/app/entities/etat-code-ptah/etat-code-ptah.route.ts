import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EtatCodePtah } from 'app/shared/model/etat-code-ptah.model';
import { EtatCodePtahService } from './etat-code-ptah.service';
import { EtatCodePtahComponent } from './etat-code-ptah.component';
import { EtatCodePtahDetailComponent } from './etat-code-ptah-detail.component';
import { EtatCodePtahUpdateComponent } from './etat-code-ptah-update.component';
import { EtatCodePtahDeletePopupComponent } from './etat-code-ptah-delete-dialog.component';
import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

@Injectable({ providedIn: 'root' })
export class EtatCodePtahResolve implements Resolve<IEtatCodePtah> {
  constructor(private service: EtatCodePtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((etatCode: HttpResponse<EtatCodePtah>) => etatCode.body));
    }
    return of(new EtatCodePtah());
  }
}

export const etatCodeRoute: Routes = [
  {
    path: 'etat-code-ptah',
    component: EtatCodePtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EtatCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'etat-code-ptah/:id/view',
    component: EtatCodePtahDetailComponent,
    resolve: {
      etatCode: EtatCodePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EtatCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'etat-code-ptah/new',
    component: EtatCodePtahUpdateComponent,
    resolve: {
      etatCode: EtatCodePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EtatCodes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'etat-code-ptah/:id/edit',
    component: EtatCodePtahUpdateComponent,
    resolve: {
      etatCode: EtatCodePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EtatCodes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const etatCodePopupRoute: Routes = [
  {
    path: 'etat-code-ptah/:id/delete',
    component: EtatCodePtahDeletePopupComponent,
    resolve: {
      etatCode: EtatCodePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EtatCodes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
