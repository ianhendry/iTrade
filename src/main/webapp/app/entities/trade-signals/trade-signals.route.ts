import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITradeSignals, TradeSignals } from 'app/shared/model/trade-signals.model';
import { TradeSignalsService } from './trade-signals.service';
import { TradeSignalsComponent } from './trade-signals.component';
import { TradeSignalsDetailComponent } from './trade-signals-detail.component';
import { TradeSignalsUpdateComponent } from './trade-signals-update.component';

@Injectable({ providedIn: 'root' })
export class TradeSignalsResolve implements Resolve<ITradeSignals> {
  constructor(private service: TradeSignalsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITradeSignals> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tradeSignals: HttpResponse<TradeSignals>) => {
          if (tradeSignals.body) {
            return of(tradeSignals.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TradeSignals());
  }
}

export const tradeSignalsRoute: Routes = [
  {
    path: '',
    component: TradeSignalsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.tradeSignals.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TradeSignalsDetailComponent,
    resolve: {
      tradeSignals: TradeSignalsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeSignals.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TradeSignalsUpdateComponent,
    resolve: {
      tradeSignals: TradeSignalsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeSignals.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TradeSignalsUpdateComponent,
    resolve: {
      tradeSignals: TradeSignalsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeSignals.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
