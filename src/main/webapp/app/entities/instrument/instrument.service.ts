import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInstrument } from 'app/shared/model/instrument.model';

type EntityResponseType = HttpResponse<IInstrument>;
type EntityArrayResponseType = HttpResponse<IInstrument[]>;

@Injectable({ providedIn: 'root' })
export class InstrumentService {
  public resourceUrl = SERVER_API_URL + 'api/instruments';

  constructor(protected http: HttpClient) {}

  create(instrument: IInstrument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instrument);
    return this.http
      .post<IInstrument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(instrument: IInstrument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(instrument);
    return this.http
      .put<IInstrument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInstrument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInstrument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(instrument: IInstrument): IInstrument {
    const copy: IInstrument = Object.assign({}, instrument, {
      dataFrom: instrument.dataFrom && instrument.dataFrom.isValid() ? instrument.dataFrom.format(DATE_FORMAT) : undefined,
      dateAdded: instrument.dateAdded && instrument.dateAdded.isValid() ? instrument.dateAdded.format(DATE_FORMAT) : undefined,
      dateInactive: instrument.dateInactive && instrument.dateInactive.isValid() ? instrument.dateInactive.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataFrom = res.body.dataFrom ? moment(res.body.dataFrom) : undefined;
      res.body.dateAdded = res.body.dateAdded ? moment(res.body.dateAdded) : undefined;
      res.body.dateInactive = res.body.dateInactive ? moment(res.body.dateInactive) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((instrument: IInstrument) => {
        instrument.dataFrom = instrument.dataFrom ? moment(instrument.dataFrom) : undefined;
        instrument.dateAdded = instrument.dateAdded ? moment(instrument.dateAdded) : undefined;
        instrument.dateInactive = instrument.dateInactive ? moment(instrument.dateInactive) : undefined;
      });
    }
    return res;
  }
}
