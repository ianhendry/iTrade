import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITradeJournalPost, TradeJournalPost } from 'app/shared/model/trade-journal-post.model';
import { TradeJournalPostService } from './trade-journal-post.service';
import { TradeJournalPostComponent } from './trade-journal-post.component';
import { TradeJournalPostDetailComponent } from './trade-journal-post-detail.component';
import { TradeJournalPostUpdateComponent } from './trade-journal-post-update.component';

@Injectable({ providedIn: 'root' })
export class TradeJournalPostResolve implements Resolve<ITradeJournalPost> {
  constructor(private service: TradeJournalPostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITradeJournalPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tradeJournalPost: HttpResponse<TradeJournalPost>) => {
          if (tradeJournalPost.body) {
            return of(tradeJournalPost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TradeJournalPost());
  }
}

export const tradeJournalPostRoute: Routes = [
  {
    path: '',
    component: TradeJournalPostComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeJournalPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TradeJournalPostDetailComponent,
    resolve: {
      tradeJournalPost: TradeJournalPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeJournalPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TradeJournalPostUpdateComponent,
    resolve: {
      tradeJournalPost: TradeJournalPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeJournalPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TradeJournalPostUpdateComponent,
    resolve: {
      tradeJournalPost: TradeJournalPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.tradeJournalPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
