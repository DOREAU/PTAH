import { IPageWebPtah } from 'app/shared/model//page-web-ptah.model';
import { ICodeAccesPtah } from 'app/shared/model//code-acces-ptah.model';

export interface IScenarioPtah {
  id?: number;
  nom?: string;
  contients?: IPageWebPtah[];
  contients?: ICodeAccesPtah[];
  estCreePourUnId?: number;
}

export class ScenarioPtah implements IScenarioPtah {
  constructor(
    public id?: number,
    public nom?: string,
    public contients?: IPageWebPtah[],
    public contients?: ICodeAccesPtah[],
    public estCreePourUnId?: number
  ) {}
}
