import { Component, OnInit, ViewChild } from '@angular/core';
import { DepartmentService } from '../../shared/services/department.service';
import { Department } from '../../shared/models/department.types';
import { DepartmentCardComponent } from './department-card/department-card.component';
import { SharedModule } from '../../shared/shared.module';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DepartmentModalComponent } from './department-modal/department-modal.component';
import { DialogService } from 'primeng/dynamicdialog';
import { DepartmentAddModalComponent } from './department-card/department-add-modal/department-add-modal.component';
import { DepartmentEditComponent } from './department-edit/department-edit.component';
import { TokenService } from '../../shared/services/token.service';

@Component({
  selector: 'app-departments',
  standalone: true,
  imports: [SharedModule, DepartmentCardComponent, DepartmentModalComponent],
  templateUrl: './departments.component.html',
  styleUrl: './departments.component.scss',
})
export class DepartmentsComponent implements OnInit {
  @ViewChild(DepartmentModalComponent) childComponent: DepartmentModalComponent;

  departments: Department[] = [];
  isAuthorized: boolean = false;

  constructor(
    private _departmentService: DepartmentService,
    private _confirmationService: ConfirmationService,
    public _dialogService: DialogService,
    private _messageService: MessageService,
    public _tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this._departmentService.getAll().subscribe(res => {
      this.departments = res;
    });
  }

  add() {
    const ref = this._dialogService.open(DepartmentAddModalComponent, {
      header: 'Add department',
      width: '600px',
    });

    ref.onClose.subscribe((data) => {
      if(!data) {
        return;
      }

      this._departmentService.getAll().subscribe(res => {
        this.departments = res;

        this._messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Department added successfully',
        });
      });
    });
  }

  details(id: number) {
    this.childComponent.open(id);
  }

  edit(id: number) {
    const ref = this._dialogService.open(DepartmentEditComponent, {
      header: 'Edit department',
      width: '600px',
      data: { data: this.departments.find(d => d.id === id) },
    });

    ref.onClose.subscribe((data) => {
      if(!data) {
        return;
      }

      this._departmentService.getAll().subscribe(res => {
        this.departments = res;

        this._messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Department edited successfully',
        });
      });
    });
  }

  delete(id: number) {
    this._confirmationService.confirm({
      message: 'Are you sure that you want to proceed?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptIcon: 'none',
      rejectIcon: 'none',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this._departmentService.delete(id).subscribe({
          next: res => {
            this.departments = this.departments.filter(d => d.id !== id);

            this._messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Department deleted successfully',
            });
          },
          error: err => {
            this._messageService.add({
              severity: 'error',
              summary: 'Error',
              detail:
                "You can't delete this department because it has employees",
            });
          },
        });
      },
      reject: () => {},
    });
  }
}
