import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DepartmentService } from '../../../../shared/services/department.service';
import { SharedModule } from '../../../../shared/shared.module';

@Component({
  selector: 'department-add-modal',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './department-add-modal.component.html',
  styleUrl: './department-add-modal.component.scss',
})
export class DepartmentAddModalComponent {
  ref: DynamicDialogRef | undefined;

  form: FormGroup;
  departmentId: number;

  constructor(
    private _departmentService: DepartmentService,
    private _formBuilder: FormBuilder,
    private _dynamicDialogConfig: DynamicDialogConfig,
    private _dynamicDialogRef: DynamicDialogRef
  ) {}

  ngOnInit(): void {
    this.form = this._formBuilder.group({
      name: [''],
    });
  }

  onSubmit() {
    this._departmentService.add(this.form.value).subscribe(() => {
      this._dynamicDialogRef?.close(true);
    });
  }
}
