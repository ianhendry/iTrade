import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWatchlist, Watchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';
import { WatchlistComponent } from './watchlist.component';
import { WatchlistDetailComponent } from './watchlist-detail.component';
import { WatchlistUpdateComponent } from './watchlist-update.component';

@Injectable({ providedIn: 'root' })
export class WatchlistResolve implements Resolve<IWatchlist> {
  constructor(private service: WatchlistService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWatchlist> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((watchlist: HttpResponse<Watchlist>) => {
          if (watchlist.body) {
            return of(watchlist.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Watchlist());
  }
}

export const watchlistRoute: Routes = [
  {
    path: '',
    component: WatchlistComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.watchlist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WatchlistDetailComponent,
    resolve: {
      watchlist: WatchlistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.watchlist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WatchlistUpdateComponent,
    resolve: {
      watchlist: WatchlistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.watchlist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WatchlistUpdateComponent,
    resolve: {
      watchlist: WatchlistResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.watchlist.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
