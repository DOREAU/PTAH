import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEquipePtah } from 'app/shared/model/equipe-ptah.model';

type EntityResponseType = HttpResponse<IEquipePtah>;
type EntityArrayResponseType = HttpResponse<IEquipePtah[]>;

@Injectable({ providedIn: 'root' })
export class EquipePtahService {
  private resourceUrl = SERVER_API_URL + 'api/equipes';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/equipes';

  constructor(private http: HttpClient) {}

  create(equipe: IEquipePtah): Observable<EntityResponseType> {
    return this.http.post<IEquipePtah>(this.resourceUrl, equipe, { observe: 'response' });
  }

  update(equipe: IEquipePtah): Observable<EntityResponseType> {
    return this.http.put<IEquipePtah>(this.resourceUrl, equipe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEquipePtah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEquipePtah[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEquipePtah[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
