import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';

type EntityResponseType = HttpResponse<IDailyAnalysisPost>;
type EntityArrayResponseType = HttpResponse<IDailyAnalysisPost[]>;

@Injectable({ providedIn: 'root' })
export class DailyAnalysisPostService {
  public resourceUrl = SERVER_API_URL + 'api/daily-analysis-posts';

  constructor(protected http: HttpClient) {}

  create(dailyAnalysisPost: IDailyAnalysisPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyAnalysisPost);
    return this.http
      .post<IDailyAnalysisPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyAnalysisPost: IDailyAnalysisPost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyAnalysisPost);
    return this.http
      .put<IDailyAnalysisPost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDailyAnalysisPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyAnalysisPost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dailyAnalysisPost: IDailyAnalysisPost): IDailyAnalysisPost {
    const copy: IDailyAnalysisPost = Object.assign({}, dailyAnalysisPost, {
      dateAdded: dailyAnalysisPost.dateAdded && dailyAnalysisPost.dateAdded.isValid() ? dailyAnalysisPost.dateAdded.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAdded = res.body.dateAdded ? moment(res.body.dateAdded) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dailyAnalysisPost: IDailyAnalysisPost) => {
        dailyAnalysisPost.dateAdded = dailyAnalysisPost.dateAdded ? moment(dailyAnalysisPost.dateAdded) : undefined;
      });
    }
    return res;
  }
}
