import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IScenarioPtah } from 'app/shared/model/scenario-ptah.model';

type EntityResponseType = HttpResponse<IScenarioPtah>;
type EntityArrayResponseType = HttpResponse<IScenarioPtah[]>;

@Injectable({ providedIn: 'root' })
export class ScenarioPtahService {
  private resourceUrl = SERVER_API_URL + 'api/scenarios';
  private resourceSearchUrl = SERVER_API_URL + 'api/_search/scenarios';

  constructor(private http: HttpClient) {}

  create(scenario: IScenarioPtah): Observable<EntityResponseType> {
    return this.http.post<IScenarioPtah>(this.resourceUrl, scenario, { observe: 'response' });
  }

  update(scenario: IScenarioPtah): Observable<EntityResponseType> {
    return this.http.put<IScenarioPtah>(this.resourceUrl, scenario, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScenarioPtah>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScenarioPtah[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScenarioPtah[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
