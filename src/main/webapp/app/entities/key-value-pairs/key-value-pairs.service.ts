import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKeyValuePairs } from 'app/shared/model/key-value-pairs.model';

type EntityResponseType = HttpResponse<IKeyValuePairs>;
type EntityArrayResponseType = HttpResponse<IKeyValuePairs[]>;

@Injectable({ providedIn: 'root' })
export class KeyValuePairsService {
  public resourceUrl = SERVER_API_URL + 'api/key-value-pairs';

  constructor(protected http: HttpClient) {}

  create(keyValuePairs: IKeyValuePairs): Observable<EntityResponseType> {
    return this.http.post<IKeyValuePairs>(this.resourceUrl, keyValuePairs, { observe: 'response' });
  }

  update(keyValuePairs: IKeyValuePairs): Observable<EntityResponseType> {
    return this.http.put<IKeyValuePairs>(this.resourceUrl, keyValuePairs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKeyValuePairs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKeyValuePairs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
