import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKeyValuePairs, KeyValuePairs } from 'app/shared/model/key-value-pairs.model';
import { KeyValuePairsService } from './key-value-pairs.service';
import { KeyValuePairsComponent } from './key-value-pairs.component';
import { KeyValuePairsDetailComponent } from './key-value-pairs-detail.component';
import { KeyValuePairsUpdateComponent } from './key-value-pairs-update.component';

@Injectable({ providedIn: 'root' })
export class KeyValuePairsResolve implements Resolve<IKeyValuePairs> {
  constructor(private service: KeyValuePairsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKeyValuePairs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((keyValuePairs: HttpResponse<KeyValuePairs>) => {
          if (keyValuePairs.body) {
            return of(keyValuePairs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KeyValuePairs());
  }
}

export const keyValuePairsRoute: Routes = [
  {
    path: '',
    component: KeyValuePairsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.keyValuePairs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KeyValuePairsDetailComponent,
    resolve: {
      keyValuePairs: KeyValuePairsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.keyValuePairs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KeyValuePairsUpdateComponent,
    resolve: {
      keyValuePairs: KeyValuePairsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.keyValuePairs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KeyValuePairsUpdateComponent,
    resolve: {
      keyValuePairs: KeyValuePairsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.keyValuePairs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
