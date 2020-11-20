import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPriceDataHistory, PriceDataHistory } from 'app/shared/model/price-data-history.model';
import { PriceDataHistoryService } from './price-data-history.service';
import { PriceDataHistoryComponent } from './price-data-history.component';
import { PriceDataHistoryDetailComponent } from './price-data-history-detail.component';
import { PriceDataHistoryUpdateComponent } from './price-data-history-update.component';

@Injectable({ providedIn: 'root' })
export class PriceDataHistoryResolve implements Resolve<IPriceDataHistory> {
  constructor(private service: PriceDataHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriceDataHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((priceDataHistory: HttpResponse<PriceDataHistory>) => {
          if (priceDataHistory.body) {
            return of(priceDataHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PriceDataHistory());
  }
}

export const priceDataHistoryRoute: Routes = [
  {
    path: '',
    component: PriceDataHistoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.priceDataHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PriceDataHistoryDetailComponent,
    resolve: {
      priceDataHistory: PriceDataHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.priceDataHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PriceDataHistoryUpdateComponent,
    resolve: {
      priceDataHistory: PriceDataHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.priceDataHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PriceDataHistoryUpdateComponent,
    resolve: {
      priceDataHistory: PriceDataHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.priceDataHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
