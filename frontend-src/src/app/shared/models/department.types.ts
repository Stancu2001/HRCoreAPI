import { Employee } from './employee.types';
import { Function } from './function.types';

export interface Department {
  id: number;
  name: string;
  functions: Function[];
  employeeList: Employee[];
}
