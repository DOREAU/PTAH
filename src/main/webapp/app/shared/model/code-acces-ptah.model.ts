export interface ICodeAccesPtah {
  id?: number;
  code?: string;
  scenarioId?: number;
}

export class CodeAccesPtah implements ICodeAccesPtah {
  constructor(public id?: number, public code?: string, public scenarioId?: number) {}
}
