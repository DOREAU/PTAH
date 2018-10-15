export interface ILienPageWebPtah {
  id?: number;
  codeSaisi?: string;
  urlCible?: string;
  pageWebId?: number;
}

export class LienPageWebPtah implements ILienPageWebPtah {
  constructor(public id?: number, public codeSaisi?: string, public urlCible?: string, public pageWebId?: number) {}
}
