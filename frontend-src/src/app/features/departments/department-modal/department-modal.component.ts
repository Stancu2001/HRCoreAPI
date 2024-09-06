import { Component } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { Department } from '../../../shared/models/department.types';
import { DepartmentService } from '../../../shared/services/department.service';
import { SharedModule } from '../../../shared/shared.module';
import { EmployeeAddModalComponent } from './employee-add-modal/employee-add-modal.component';
import { FunctionAddModalComponent } from './function-add-modal/function-add-modal.component';
import { ConfirmationService } from 'primeng/api';
import { FunctionService } from '../../../shared/services/function.service';
import { Employee } from '../../../shared/models/employee.types';
import { EmployeeService } from '../../../shared/services/employee.service';

@Component({
  selector: 'department-modal',
  standalone: true,
  imports: [SharedModule, EmployeeAddModalComponent],
  templateUrl: './department-modal.component.html',
  styleUrl: './department-modal.component.scss',
})
export class DepartmentModalComponent {
  visible = false;
  department?: Department;
  selectedEmployee: Employee;
  viewEmployeeData = false;

  constructor(
    private _departmentService: DepartmentService,
    private _functionService: FunctionService,
    private _employeeService: EmployeeService,
    private _confirmationService: ConfirmationService,
    public _dialogService: DialogService
  ) {}

  open(id: number): void {
    this.loadDepartment(id);
  }

  openAddEmployeeModal() {
    const ref = this._dialogService.open(EmployeeAddModalComponent, {
      header: 'Add employee',
      width: '600px',
      data: {
        departmentId: this.department?.id,
        functions: this.department?.functions,
      },
    });

    ref.onClose.subscribe((succes: boolean) => {
      if (succes == true) {
        this.loadDepartment(this.department?.id!);
      }
    });
  }

  openAddFunctionModal() {
    const ref = this._dialogService.open(FunctionAddModalComponent, {
      header: 'Add function',
      width: '600px',
      data: {
        departmentId: this.department?.id,
      },
    });

    ref.onClose.subscribe((succes: boolean) => {
      if (succes == true) {
        this.loadDepartment(this.department?.id!);
      }
    });
  }

  openEditFunctionModal(row: number) {
    const ref = this._dialogService.open(FunctionAddModalComponent, {
      header: 'Edit function',
      width: '600px',
      data: {
        edit: true,
        departmentId: this.department?.id,
        function: this.department?.functions[row],
      },
    });

    ref.onClose.subscribe((succes: boolean) => {
      if (succes == true) {
        this.loadDepartment(this.department?.id!);
      }
    });
  }

  deleteEmployee(row: number) {
    this._confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this._employeeService
          .delete(this.department?.employeeList[row].idEmployee!)
          .subscribe(() => {
            this.loadDepartment(this.department?.id!);
          });
      },
      reject: () => {},
    });
  }

  deleteFunction(row: number) {
    this._confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this._functionService
          .delete(this.department?.functions[row].functionId!)
          .subscribe(() => {
            this.loadDepartment(this.department?.id!);
          });
      },
      reject: () => {},
    });
  }

  onRowSelect(employee: any) {
    this.selectedEmployee = employee.data;
    this.viewEmployeeData = true;
  }

  loadDepartment(id: number) {
    this._departmentService.get(id).subscribe(department => {
      this.department = department;
      this.visible = true;

      this.department.employeeList.forEach(employee => {
        employee.timeLogs.map(timeLog => {
          const d = timeLog.timestamp;

          (timeLog as any).display = new Date(
            d[0],
            d[1] - 1,
            d[2],
            d[3],
            d[4],
            d[5]
          );
        });
      });
    });
  }
}
