import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISignalSequences, SignalSequences } from 'app/shared/model/signal-sequences.model';
import { SignalSequencesService } from './signal-sequences.service';
import { SignalSequencesComponent } from './signal-sequences.component';
import { SignalSequencesDetailComponent } from './signal-sequences-detail.component';
import { SignalSequencesUpdateComponent } from './signal-sequences-update.component';

@Injectable({ providedIn: 'root' })
export class SignalSequencesResolve implements Resolve<ISignalSequences> {
  constructor(private service: SignalSequencesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISignalSequences> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((signalSequences: HttpResponse<SignalSequences>) => {
          if (signalSequences.body) {
            return of(signalSequences.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SignalSequences());
  }
}

export const signalSequencesRoute: Routes = [
  {
    path: '',
    component: SignalSequencesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'iTradeApp.signalSequences.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SignalSequencesDetailComponent,
    resolve: {
      signalSequences: SignalSequencesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalSequences.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SignalSequencesUpdateComponent,
    resolve: {
      signalSequences: SignalSequencesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalSequences.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SignalSequencesUpdateComponent,
    resolve: {
      signalSequences: SignalSequencesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.signalSequences.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
