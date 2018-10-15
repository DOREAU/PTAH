import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EquipePtah } from 'app/shared/model/equipe-ptah.model';
import { EquipePtahService } from './equipe-ptah.service';
import { EquipePtahComponent } from './equipe-ptah.component';
import { EquipePtahDetailComponent } from './equipe-ptah-detail.component';
import { EquipePtahUpdateComponent } from './equipe-ptah-update.component';
import { EquipePtahDeletePopupComponent } from './equipe-ptah-delete-dialog.component';
import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';

@Injectable({ providedIn: 'root' })
export class EquipePtahResolve implements Resolve<IEquipePtah> {
  constructor(private service: EquipePtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((equipe: HttpResponse<EquipePtah>) => equipe.body));
    }
    return of(new EquipePtah());
  }
}

export const equipeRoute: Routes = [
  {
    path: 'equipe-ptah',
    component: EquipePtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'equipe-ptah/:id/view',
    component: EquipePtahDetailComponent,
    resolve: {
      equipe: EquipePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'equipe-ptah/new',
    component: EquipePtahUpdateComponent,
    resolve: {
      equipe: EquipePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'equipe-ptah/:id/edit',
    component: EquipePtahUpdateComponent,
    resolve: {
      equipe: EquipePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const equipePopupRoute: Routes = [
  {
    path: 'equipe-ptah/:id/delete',
    component: EquipePtahDeletePopupComponent,
    resolve: {
      equipe: EquipePtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Equipes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
