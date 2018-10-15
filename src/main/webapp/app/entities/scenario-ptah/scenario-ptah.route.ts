import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ScenarioPtah } from 'app/shared/model/scenario-ptah.model';
import { ScenarioPtahService } from './scenario-ptah.service';
import { ScenarioPtahComponent } from './scenario-ptah.component';
import { ScenarioPtahDetailComponent } from './scenario-ptah-detail.component';
import { ScenarioPtahUpdateComponent } from './scenario-ptah-update.component';
import { ScenarioPtahDeletePopupComponent } from './scenario-ptah-delete-dialog.component';
import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';

@Injectable({ providedIn: 'root' })
export class ScenarioPtahResolve implements Resolve<IScenarioPtah> {
  constructor(private service: ScenarioPtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((scenario: HttpResponse<ScenarioPtah>) => scenario.body));
    }
    return of(new ScenarioPtah());
  }
}

export const scenarioRoute: Routes = [
  {
    path: 'scenario-ptah',
    component: ScenarioPtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Scenarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'scenario-ptah/:id/view',
    component: ScenarioPtahDetailComponent,
    resolve: {
      scenario: ScenarioPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Scenarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'scenario-ptah/new',
    component: ScenarioPtahUpdateComponent,
    resolve: {
      scenario: ScenarioPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Scenarios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'scenario-ptah/:id/edit',
    component: ScenarioPtahUpdateComponent,
    resolve: {
      scenario: ScenarioPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Scenarios'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const scenarioPopupRoute: Routes = [
  {
    path: 'scenario-ptah/:id/delete',
    component: ScenarioPtahDeletePopupComponent,
    resolve: {
      scenario: ScenarioPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Scenarios'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
