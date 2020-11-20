import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMt4Trade, Mt4Trade } from 'app/shared/model/mt-4-trade.model';
import { Mt4TradeService } from './mt-4-trade.service';
import { Mt4TradeComponent } from './mt-4-trade.component';
import { Mt4TradeDetailComponent } from './mt-4-trade-detail.component';
import { Mt4TradeUpdateComponent } from './mt-4-trade-update.component';

@Injectable({ providedIn: 'root' })
export class Mt4TradeResolve implements Resolve<IMt4Trade> {
  constructor(private service: Mt4TradeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMt4Trade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mt4Trade: HttpResponse<Mt4Trade>) => {
          if (mt4Trade.body) {
            return of(mt4Trade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mt4Trade());
  }
}

export const mt4TradeRoute: Routes = [
  {
    path: '',
    component: Mt4TradeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.mt4Trade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Mt4TradeDetailComponent,
    resolve: {
      mt4Trade: Mt4TradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Trade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Mt4TradeUpdateComponent,
    resolve: {
      mt4Trade: Mt4TradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Trade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Mt4TradeUpdateComponent,
    resolve: {
      mt4Trade: Mt4TradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.mt4Trade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
