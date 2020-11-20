import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWatchlist } from 'app/shared/model/watchlist.model';

type EntityResponseType = HttpResponse<IWatchlist>;
type EntityArrayResponseType = HttpResponse<IWatchlist[]>;

@Injectable({ providedIn: 'root' })
export class WatchlistService {
  public resourceUrl = SERVER_API_URL + 'api/watchlists';

  constructor(protected http: HttpClient) {}

  create(watchlist: IWatchlist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(watchlist);
    return this.http
      .post<IWatchlist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(watchlist: IWatchlist): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(watchlist);
    return this.http
      .put<IWatchlist>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWatchlist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWatchlist[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(watchlist: IWatchlist): IWatchlist {
    const copy: IWatchlist = Object.assign({}, watchlist, {
      dateCreated: watchlist.dateCreated && watchlist.dateCreated.isValid() ? watchlist.dateCreated.format(DATE_FORMAT) : undefined,
      dateInactive: watchlist.dateInactive && watchlist.dateInactive.isValid() ? watchlist.dateInactive.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreated = res.body.dateCreated ? moment(res.body.dateCreated) : undefined;
      res.body.dateInactive = res.body.dateInactive ? moment(res.body.dateInactive) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((watchlist: IWatchlist) => {
        watchlist.dateCreated = watchlist.dateCreated ? moment(watchlist.dateCreated) : undefined;
        watchlist.dateInactive = watchlist.dateInactive ? moment(watchlist.dateInactive) : undefined;
      });
    }
    return res;
  }
}
