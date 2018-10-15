import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPageWebPtah } from 'app/shared/model/page-web-ptah.model';

type EntityResponseType = HttpResponse<IPageWebPtah>;
type EntityArrayResponseType = HttpResponse<IPageWebPtah[]>;

@Injectable({ providedIn: 'root' })
export class PageWebPtahService {
  private resourceUrl = SERVER_API_URL + 'api/page-webs';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/page-webs';

  constructor(private http: HttpClient) {}

  create(pageWeb: IPageWebPtah): Observable<EntityResponseType> {
    return this.http.post<IPageWebPtah>(this.resourceUrl, pageWeb, { observe: 'response' });
  }

  update(pageWeb: IPageWebPtah): Observable<EntityResponseType> {
    return this.http.put<IPageWebPtah>(this.resourceUrl, pageWeb, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageWebPtah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageWebPtah[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageWebPtah[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
