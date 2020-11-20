import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInstrument, Instrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';
import { InstrumentComponent } from './instrument.component';
import { InstrumentDetailComponent } from './instrument-detail.component';
import { InstrumentUpdateComponent } from './instrument-update.component';

@Injectable({ providedIn: 'root' })
export class InstrumentResolve implements Resolve<IInstrument> {
  constructor(private service: InstrumentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstrument> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((instrument: HttpResponse<Instrument>) => {
          if (instrument.body) {
            return of(instrument.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Instrument());
  }
}

export const instrumentRoute: Routes = [
  {
    path: '',
    component: InstrumentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.instrument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InstrumentDetailComponent,
    resolve: {
      instrument: InstrumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.instrument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InstrumentUpdateComponent,
    resolve: {
      instrument: InstrumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.instrument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InstrumentUpdateComponent,
    resolve: {
      instrument: InstrumentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.instrument.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
