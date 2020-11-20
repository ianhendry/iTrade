import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISignalService, SignalService } from 'app/shared/model/signal-service.model';
import { SignalServiceService } from './signal-service.service';
import { SignalServiceComponent } from './signal-service.component';
import { SignalServiceDetailComponent } from './signal-service-detail.component';
import { SignalServiceUpdateComponent } from './signal-service-update.component';

@Injectable({ providedIn: 'root' })
export class SignalServiceResolve implements Resolve<ISignalService> {
  constructor(private service: SignalServiceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISignalService> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((signalService: HttpResponse<SignalService>) => {
          if (signalService.body) {
            return of(signalService.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SignalService());
  }
}

export const signalServiceRoute: Routes = [
  {
    path: '',
    component: SignalServiceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.signalService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SignalServiceDetailComponent,
    resolve: {
      signalService: SignalServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SignalServiceUpdateComponent,
    resolve: {
      signalService: SignalServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SignalServiceUpdateComponent,
    resolve: {
      signalService: SignalServiceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalService.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
