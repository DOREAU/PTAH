import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PersonnePtah } from './personne-ptah.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonnePtahService {

    private resourceUrl =  SERVER_API_URL + 'api/personnes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/personnes';

    constructor(private http: Http) { }

    create(personne: PersonnePtah): Observable<PersonnePtah> {
        const copy = this.convert(personne);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(personne: PersonnePtah): Observable<PersonnePtah> {
        const copy = this.convert(personne);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PersonnePtah> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to PersonnePtah.
     */
    private convertItemFromServer(json: any): PersonnePtah {
        const entity: PersonnePtah = Object.assign(new PersonnePtah(), json);
        return entity;
    }

    /**
     * Convert a PersonnePtah to a JSON which can be sent to the server.
     */
    private convert(personne: PersonnePtah): PersonnePtah {
        const copy: PersonnePtah = Object.assign({}, personne);
        return copy;
    }
}
