import { ILienPageWebPtah } from 'app/shared/model//lien-page-web-ptah.model';

export interface IPageWebPtah {
  id?: number;
  urlPage?: string;
  scenarioId?: number;
  estContenueDans?: ILienPageWebPtah[];
  pageDebutId?: number;
  pageFinId?: number;
}

export class PageWebPtah implements IPageWebPtah {
  constructor(
    public id?: number,
    public urlPage?: string,
    public scenarioId?: number,
    public estContenueDans?: ILienPageWebPtah[],
    public pageDebutId?: number,
    public pageFinId?: number
  ) {}
}
