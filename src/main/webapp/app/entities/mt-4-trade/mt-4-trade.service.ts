import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMt4Trade } from 'app/shared/model/mt-4-trade.model';

type EntityResponseType = HttpResponse<IMt4Trade>;
type EntityArrayResponseType = HttpResponse<IMt4Trade[]>;

@Injectable({ providedIn: 'root' })
export class Mt4TradeService {
  public resourceUrl = SERVER_API_URL + 'api/mt-4-trades';

  constructor(protected http: HttpClient) {}

  create(mt4Trade: IMt4Trade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mt4Trade);
    return this.http
      .post<IMt4Trade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mt4Trade: IMt4Trade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mt4Trade);
    return this.http
      .put<IMt4Trade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMt4Trade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMt4Trade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mt4Trade: IMt4Trade): IMt4Trade {
    const copy: IMt4Trade = Object.assign({}, mt4Trade, {
      openTime: mt4Trade.openTime && mt4Trade.openTime.isValid() ? mt4Trade.openTime.toJSON() : undefined,
      closeTime: mt4Trade.closeTime && mt4Trade.closeTime.isValid() ? mt4Trade.closeTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.openTime = res.body.openTime ? moment(res.body.openTime) : undefined;
      res.body.closeTime = res.body.closeTime ? moment(res.body.closeTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mt4Trade: IMt4Trade) => {
        mt4Trade.openTime = mt4Trade.openTime ? moment(mt4Trade.openTime) : undefined;
        mt4Trade.closeTime = mt4Trade.closeTime ? moment(mt4Trade.closeTime) : undefined;
      });
    }
    return res;
  }
}
