import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISignalSequences } from 'app/shared/model/signal-sequences.model';

type EntityResponseType = HttpResponse<ISignalSequences>;
type EntityArrayResponseType = HttpResponse<ISignalSequences[]>;

@Injectable({ providedIn: 'root' })
export class SignalSequencesService {
  public resourceUrl = SERVER_API_URL + 'api/signal-sequences';

  constructor(protected http: HttpClient) {}

  create(signalSequences: ISignalSequences): Observable<EntityResponseType> {
    return this.http.post<ISignalSequences>(this.resourceUrl, signalSequences, { observe: 'response' });
  }

  update(signalSequences: ISignalSequences): Observable<EntityResponseType> {
    return this.http.put<ISignalSequences>(this.resourceUrl, signalSequences, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISignalSequences>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISignalSequences[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
