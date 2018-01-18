import { BaseEntity } from './../../shared';

export class ApplicationPtah implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public resume?: string,
        public responsableId?: number,
    ) {
    }
}
