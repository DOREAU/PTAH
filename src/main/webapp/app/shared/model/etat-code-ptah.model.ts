export interface IEtatCodePtah {
  id?: number;
  key?: number;
  value?: string;
  aUnEtatDonneId?: number;
}

export class EtatCodePtah implements IEtatCodePtah {
  constructor(public id?: number, public key?: number, public value?: string, public aUnEtatDonneId?: number) {}
}
