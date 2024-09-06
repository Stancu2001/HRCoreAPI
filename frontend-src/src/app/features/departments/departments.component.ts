import { Component, OnInit, ViewChild } from '@angular/core';
import { DepartmentService } from '../../shared/services/department.service';
import { Department } from '../../shared/models/department.types';
import { DepartmentCardComponent } from './department-card/department-card.component';
import { SharedModule } from '../../shared/shared.module';
import { ConfirmationService } from 'primeng/api';
import { DepartmentModalComponent } from './department-modal/department-modal.component';
import { DialogService } from 'primeng/dynamicdialog';
import { DepartmentAddModalComponent } from './department-card/department-add-modal/department-add-modal.component';

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

  constructor(
    private _departmentService: DepartmentService,
    private _confirmationService: ConfirmationService,
    public _dialogService: DialogService
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

    ref.onClose.subscribe(() => {
      this._departmentService.getAll().subscribe(res => {
        this.departments = res;
      });
    });
  }

  edit(id: number) {
    this.childComponent.open(id);
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
        this._departmentService.delete(id).subscribe(() => {
          this.departments = this.departments.filter(d => d.id !== id);
        });
      },
      reject: () => {},
    });
  }
}
