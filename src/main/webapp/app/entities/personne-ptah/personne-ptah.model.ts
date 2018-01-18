import { BaseEntity } from './../../shared';

export class PersonnePtah implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
    ) {
    }
}
