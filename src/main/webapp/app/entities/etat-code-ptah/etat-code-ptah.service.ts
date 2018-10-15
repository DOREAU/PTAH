import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEtatCodePtah } from 'app/shared/model/etat-code-ptah.model';

type EntityResponseType = HttpResponse<IEtatCodePtah>;
type EntityArrayResponseType = HttpResponse<IEtatCodePtah[]>;

@Injectable({ providedIn: 'root' })
export class EtatCodePtahService {
  private resourceUrl = SERVER_API_URL + 'api/etat-codes';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/etat-codes';

  constructor(private http: HttpClient) {}

  create(etatCode: IEtatCodePtah): Observable<EntityResponseType> {
    return this.http.post<IEtatCodePtah>(this.resourceUrl, etatCode, { observe: 'response' });
  }

  update(etatCode: IEtatCodePtah): Observable<EntityResponseType> {
    return this.http.put<IEtatCodePtah>(this.resourceUrl, etatCode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtatCodePtah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatCodePtah[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtatCodePtah[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
