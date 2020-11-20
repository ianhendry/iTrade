import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMt4Account, Mt4Account } from 'app/shared/model/mt-4-account.model';
import { Mt4AccountService } from './mt-4-account.service';
import { Mt4AccountComponent } from './mt-4-account.component';
import { Mt4AccountDetailComponent } from './mt-4-account-detail.component';
import { Mt4AccountUpdateComponent } from './mt-4-account-update.component';

@Injectable({ providedIn: 'root' })
export class Mt4AccountResolve implements Resolve<IMt4Account> {
  constructor(private service: Mt4AccountService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMt4Account> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mt4Account: HttpResponse<Mt4Account>) => {
          if (mt4Account.body) {
            return of(mt4Account.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mt4Account());
  }
}

export const mt4AccountRoute: Routes = [
  {
    path: '',
    component: Mt4AccountComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.mt4Account.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Mt4AccountDetailComponent,
    resolve: {
      mt4Account: Mt4AccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Account.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Mt4AccountUpdateComponent,
    resolve: {
      mt4Account: Mt4AccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Account.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Mt4AccountUpdateComponent,
    resolve: {
      mt4Account: Mt4AccountResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Account.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
