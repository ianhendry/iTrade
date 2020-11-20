import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPriceDataHistory } from 'app/shared/model/price-data-history.model';

type EntityResponseType = HttpResponse<IPriceDataHistory>;
type EntityArrayResponseType = HttpResponse<IPriceDataHistory[]>;

@Injectable({ providedIn: 'root' })
export class PriceDataHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/price-data-histories';

  constructor(protected http: HttpClient) {}

  create(priceDataHistory: IPriceDataHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceDataHistory);
    return this.http
      .post<IPriceDataHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(priceDataHistory: IPriceDataHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(priceDataHistory);
    return this.http
      .put<IPriceDataHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPriceDataHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPriceDataHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(priceDataHistory: IPriceDataHistory): IPriceDataHistory {
    const copy: IPriceDataHistory = Object.assign({}, priceDataHistory, {
      priceDate:
        priceDataHistory.priceDate && priceDataHistory.priceDate.isValid() ? priceDataHistory.priceDate.format(DATE_FORMAT) : undefined,
      priceTime: priceDataHistory.priceTime && priceDataHistory.priceTime.isValid() ? priceDataHistory.priceTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.priceDate = res.body.priceDate ? moment(res.body.priceDate) : undefined;
      res.body.priceTime = res.body.priceTime ? moment(res.body.priceTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((priceDataHistory: IPriceDataHistory) => {
        priceDataHistory.priceDate = priceDataHistory.priceDate ? moment(priceDataHistory.priceDate) : undefined;
        priceDataHistory.priceTime = priceDataHistory.priceTime ? moment(priceDataHistory.priceTime) : undefined;
      });
    }
    return res;
  }
}
