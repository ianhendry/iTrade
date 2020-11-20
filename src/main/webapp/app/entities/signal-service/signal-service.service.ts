import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISignalService } from 'app/shared/model/signal-service.model';

type EntityResponseType = HttpResponse<ISignalService>;
type EntityArrayResponseType = HttpResponse<ISignalService[]>;

@Injectable({ providedIn: 'root' })
export class SignalServiceService {
  public resourceUrl = SERVER_API_URL + 'api/signal-services';

  constructor(protected http: HttpClient) {}

  create(signalService: ISignalService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(signalService);
    return this.http
      .post<ISignalService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(signalService: ISignalService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(signalService);
    return this.http
      .put<ISignalService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISignalService>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISignalService[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(signalService: ISignalService): ISignalService {
    const copy: ISignalService = Object.assign({}, signalService, {
      alertDate: signalService.alertDate && signalService.alertDate.isValid() ? signalService.alertDate.format(DATE_FORMAT) : undefined,
      alertTime: signalService.alertTime && signalService.alertTime.isValid() ? signalService.alertTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.alertDate = res.body.alertDate ? moment(res.body.alertDate) : undefined;
      res.body.alertTime = res.body.alertTime ? moment(res.body.alertTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((signalService: ISignalService) => {
        signalService.alertDate = signalService.alertDate ? moment(signalService.alertDate) : undefined;
        signalService.alertTime = signalService.alertTime ? moment(signalService.alertTime) : undefined;
      });
    }
    return res;
  }
}
