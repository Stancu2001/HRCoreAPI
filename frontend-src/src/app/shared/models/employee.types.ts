import { Time } from '@angular/common';
import { Address } from './address.types';

export interface Employee {
  idEmployee: number;
  email: string;
  firstName: string;
  lastName: string;
  age: number;
  phone: string;
  nationality: string;
  gender: string;
  registered: number[];
  cnp: string;
  remark: string;
  address: Address;
  departmentalFunctions: Function;
  salaries: Salary[];
  timeLogs: TimeLog[];
}

export interface Salary {
  id: number;
  salary: number;
  effectiveFrom: number[];
  effectiveTo: number[];
}

export interface TimeLog {
  id: number;
  timestamp: number[];
  type: string;
}
