export interface IEquipePtah {
  id?: number;
  nom?: string;
  aUnId?: number;
}

export class EquipePtah implements IEquipePtah {
  constructor(public id?: number, public nom?: string, public aUnId?: number) {}
}
