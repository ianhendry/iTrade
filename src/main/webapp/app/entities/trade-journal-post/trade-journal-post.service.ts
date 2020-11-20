import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITradeJournalPost } from 'app/shared/model/trade-journal-post.model';

type EntityResponseType = HttpResponse<ITradeJournalPost>;
type EntityArrayResponseType = HttpResponse<ITradeJournalPost[]>;

@Injectable({ providedIn: 'root' })
export class TradeJournalPostService {
  public resourceUrl = SERVER_API_URL + 'api/trade-journal-posts';

  constructor(protected http: HttpClient) {}

  create(tradeJournalPost: ITradeJournalPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradeJournalPost);
    return this.http
      .post<ITradeJournalPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tradeJournalPost: ITradeJournalPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tradeJournalPost);
    return this.http
      .put<ITradeJournalPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITradeJournalPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITradeJournalPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tradeJournalPost: ITradeJournalPost): ITradeJournalPost {
    const copy: ITradeJournalPost = Object.assign({}, tradeJournalPost, {
      dateAdded:
        tradeJournalPost.dateAdded && tradeJournalPost.dateAdded.isValid() ? tradeJournalPost.dateAdded.format(DATE_FORMAT) : undefined,
      dateApproved:
        tradeJournalPost.dateApproved && tradeJournalPost.dateApproved.isValid()
          ? tradeJournalPost.dateApproved.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded ? moment(res.body.dateAdded) : undefined;
      res.body.dateApproved = res.body.dateApproved ? moment(res.body.dateApproved) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tradeJournalPost: ITradeJournalPost) => {
        tradeJournalPost.dateAdded = tradeJournalPost.dateAdded ? moment(tradeJournalPost.dateAdded) : undefined;
        tradeJournalPost.dateApproved = tradeJournalPost.dateApproved ? moment(tradeJournalPost.dateApproved) : undefined;
      });
    }
    return res;
  }
}
