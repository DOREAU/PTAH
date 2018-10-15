import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILienPageWebPtah } from 'app/shared/model/lien-page-web-ptah.model';

type EntityResponseType = HttpResponse<ILienPageWebPtah>;
type EntityArrayResponseType = HttpResponse<ILienPageWebPtah[]>;

@Injectable({ providedIn: 'root' })
export class LienPageWebPtahService {
  private resourceUrl = SERVER_API_URL + 'api/lien-page-webs';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/lien-page-webs';

  constructor(private http: HttpClient) {}

  create(lienPageWeb: ILienPageWebPtah): Observable<EntityResponseType> {
    return this.http.post<ILienPageWebPtah>(this.resourceUrl, lienPageWeb, { observe: 'response' });
  }

  update(lienPageWeb: ILienPageWebPtah): Observable<EntityResponseType> {
    return this.http.put<ILienPageWebPtah>(this.resourceUrl, lienPageWeb, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILienPageWebPtah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILienPageWebPtah[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILienPageWebPtah[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
