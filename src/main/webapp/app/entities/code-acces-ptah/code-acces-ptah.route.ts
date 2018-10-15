import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';
import { CodeAccesPtahService } from './code-acces-ptah.service';
import { CodeAccesPtahComponent } from './code-acces-ptah.component';
import { CodeAccesPtahDetailComponent } from './code-acces-ptah-detail.component';
import { CodeAccesPtahUpdateComponent } from './code-acces-ptah-update.component';
import { CodeAccesPtahDeletePopupComponent } from './code-acces-ptah-delete-dialog.component';
import { ICodeAccesPtah } from 'app/shared/model/code-acces-ptah.model';

@Injectable({ providedIn: 'root' })
export class CodeAccesPtahResolve implements Resolve<ICodeAccesPtah> {
  constructor(private service: CodeAccesPtahService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(map((codeAcces: HttpResponse<CodeAccesPtah>) => codeAcces.body));
    }
    return of(new CodeAccesPtah());
  }
}

export const codeAccesRoute: Routes = [
  {
    path: 'code-acces-ptah',
    component: CodeAccesPtahComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodeAcces'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'code-acces-ptah/:id/view',
    component: CodeAccesPtahDetailComponent,
    resolve: {
      codeAcces: CodeAccesPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodeAcces'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'code-acces-ptah/new',
    component: CodeAccesPtahUpdateComponent,
    resolve: {
      codeAcces: CodeAccesPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodeAcces'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'code-acces-ptah/:id/edit',
    component: CodeAccesPtahUpdateComponent,
    resolve: {
      codeAcces: CodeAccesPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodeAcces'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const codeAccesPopupRoute: Routes = [
  {
    path: 'code-acces-ptah/:id/delete',
    component: CodeAccesPtahDeletePopupComponent,
    resolve: {
      codeAcces: CodeAccesPtahResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodeAcces'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
