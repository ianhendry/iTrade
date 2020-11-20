import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISiteAccount, SiteAccount } from 'app/shared/model/site-account.model';
import { SiteAccountService } from './site-account.service';
import { SiteAccountComponent } from './site-account.component';
import { SiteAccountDetailComponent } from './site-account-detail.component';
import { SiteAccountUpdateComponent } from './site-account-update.component';

@Injectable({ providedIn: 'root' })
export class SiteAccountResolve implements Resolve<ISiteAccount> {
  constructor(private service: SiteAccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiteAccount> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((siteAccount: HttpResponse<SiteAccount>) => {
          if (siteAccount.body) {
            return of(siteAccount.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiteAccount());
  }
}

export const siteAccountRoute: Routes = [
  {
    path: '',
    component: SiteAccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.siteAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiteAccountDetailComponent,
    resolve: {
      siteAccount: SiteAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.siteAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiteAccountUpdateComponent,
    resolve: {
      siteAccount: SiteAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.siteAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiteAccountUpdateComponent,
    resolve: {
      siteAccount: SiteAccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.siteAccount.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
