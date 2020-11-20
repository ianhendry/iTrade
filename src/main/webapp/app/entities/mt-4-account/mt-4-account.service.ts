import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMt4Account } from 'app/shared/model/mt-4-account.model';

type EntityResponseType = HttpResponse<IMt4Account>;
type EntityArrayResponseType = HttpResponse<IMt4Account[]>;

@Injectable({ providedIn: 'root' })
export class Mt4AccountService {
  public resourceUrl = SERVER_API_URL + 'api/mt-4-accounts';

  constructor(protected http: HttpClient) {}

  create(mt4Account: IMt4Account): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mt4Account);
    return this.http
      .post<IMt4Account>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mt4Account: IMt4Account): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mt4Account);
    return this.http
      .put<IMt4Account>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMt4Account>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMt4Account[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mt4Account: IMt4Account): IMt4Account {
    const copy: IMt4Account = Object.assign({}, mt4Account, {
      accountCloseDate:
        mt4Account.accountCloseDate && mt4Account.accountCloseDate.isValid() ? mt4Account.accountCloseDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.accountCloseDate = res.body.accountCloseDate ? moment(res.body.accountCloseDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mt4Account: IMt4Account) => {
        mt4Account.accountCloseDate = mt4Account.accountCloseDate ? moment(mt4Account.accountCloseDate) : undefined;
      });
    }
    return res;
  }
}
