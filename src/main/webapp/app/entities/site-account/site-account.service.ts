import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISiteAccount } from 'app/shared/model/site-account.model';

type EntityResponseType = HttpResponse<ISiteAccount>;
type EntityArrayResponseType = HttpResponse<ISiteAccount[]>;

@Injectable({ providedIn: 'root' })
export class SiteAccountService {
  public resourceUrl = SERVER_API_URL + 'api/site-accounts';

  constructor(protected http: HttpClient) {}

  create(siteAccount: ISiteAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(siteAccount);
    return this.http
      .post<ISiteAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(siteAccount: ISiteAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(siteAccount);
    return this.http
      .put<ISiteAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISiteAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISiteAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(siteAccount: ISiteAccount): ISiteAccount {
    const copy: ISiteAccount = Object.assign({}, siteAccount, {
      accountOpenDate:
        siteAccount.accountOpenDate && siteAccount.accountOpenDate.isValid() ? siteAccount.accountOpenDate.format(DATE_FORMAT) : undefined,
      accountCloseDate:
        siteAccount.accountCloseDate && siteAccount.accountCloseDate.isValid()
          ? siteAccount.accountCloseDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.accountOpenDate = res.body.accountOpenDate ? moment(res.body.accountOpenDate) : undefined;
      res.body.accountCloseDate = res.body.accountCloseDate ? moment(res.body.accountCloseDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((siteAccount: ISiteAccount) => {
        siteAccount.accountOpenDate = siteAccount.accountOpenDate ? moment(siteAccount.accountOpenDate) : undefined;
        siteAccount.accountCloseDate = siteAccount.accountCloseDate ? moment(siteAccount.accountCloseDate) : undefined;
      });
    }
    return res;
  }
}
