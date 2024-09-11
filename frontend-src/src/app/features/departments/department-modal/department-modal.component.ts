import { Component } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { Department } from '../../../shared/models/department.types';
import { DepartmentService } from '../../../shared/services/department.service';
import { SharedModule } from '../../../shared/shared.module';
import { EmployeeAddModalComponent } from './employee-add-modal/employee-add-modal.component';
import { FunctionAddModalComponent } from './function-add-modal/function-add-modal.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { FunctionService } from '../../../shared/services/function.service';
import { Employee, Payroll } from '../../../shared/models/employee.types';
import { EmployeeService } from '../../../shared/services/employee.service';
import { Observable } from 'rxjs';
import { MonthFromNumberPipe } from '../../../core/pipes/month-from-number.pipe';
import { PayrollService } from '../../../shared/services/payroll.service';
import { SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'department-modal',
  standalone: true,
  imports: [SharedModule, EmployeeAddModalComponent, MonthFromNumberPipe],
  templateUrl: './department-modal.component.html',
  styleUrl: './department-modal.component.scss',
})
export class DepartmentModalComponent {
  visible = false;
  department?: Department;
  selectedEmployee: Employee;
  viewEmployeeData = false;
  salary?: number | null = null;
  qrCodeDownloadLink: SafeUrl | undefined;

  constructor(
    private _departmentService: DepartmentService,
    private _functionService: FunctionService,
    private _employeeService: EmployeeService,
    private _confirmationService: ConfirmationService,
    public _dialogService: DialogService,
    private _messageService: MessageService,
    private _payrollService: PayrollService
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

  openEditEmployeeModal(row: number) {
    const ref = this._dialogService.open(EmployeeAddModalComponent, {
      header: 'Edit employee',
      width: '600px',
      data: {
        edit: true,
        departmentId: this.department?.id,
        functions: this.department?.functions,
        employee: this.department?.employeeList[row],
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
          .subscribe({
            next: () => {
              this.loadDepartment(this.department?.id!);

              this._messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Function deleted successfully',
              });
            },
            error: () => {
              this._messageService.add({
                severity: 'error',
                summary: 'Error',
                detail:
                  'You cannot delete a function that is assigned to an employee',
              });
            },
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

      this.salary = null;
    });
  }

  setSalary(employee: Employee, salary?: number) {
    if (this.salary == null) {
      return;
    }

    if (employee.salaries.length > 0) {
      this._employeeService.modifySalary(employee, salary!).subscribe(() => {
        this.loadDepartment(this.department?.id!);
      });
    } else {
      this._employeeService.setSalary(employee, salary!).subscribe(() => {
        this.loadDepartment(this.department?.id!);
      });
    }
  }

  calculateNet(employee: Employee) {
    this._payrollService.calculate(employee.idEmployee!).subscribe({
      next: (payroll: any) => {
        this.loadDepartment(this.department?.id!);

        this._messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Net salary calculated successfully',
        });
      },
      error: () => {
        this._messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'The salary was already calculated for previous month',
        });
      },
    });
  }

  onChangeURL(url: SafeUrl) {
    this.qrCodeDownloadLink = url;
  }
}
