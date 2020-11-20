import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITradeSignals } from 'app/shared/model/trade-signals.model';

type EntityResponseType = HttpResponse<ITradeSignals>;
type EntityArrayResponseType = HttpResponse<ITradeSignals[]>;

@Injectable({ providedIn: 'root' })
export class TradeSignalsService {
  public resourceUrl = SERVER_API_URL + 'api/trade-signals';

  constructor(protected http: HttpClient) {}

  create(tradeSignals: ITradeSignals): Observable<EntityResponseType> {
    return this.http.post<ITradeSignals>(this.resourceUrl, tradeSignals, { observe: 'response' });
  }

  update(tradeSignals: ITradeSignals): Observable<EntityResponseType> {
    return this.http.put<ITradeSignals>(this.resourceUrl, tradeSignals, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITradeSignals>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITradeSignals[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
