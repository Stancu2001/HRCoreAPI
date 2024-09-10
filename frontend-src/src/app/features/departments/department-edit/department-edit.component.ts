import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { SharedModule } from '../../../shared/shared.module';
import { DepartmentService } from '../../../shared/services/department.service';

@Component({
  selector: 'app-department-edit',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './department-edit.component.html',
  styleUrl: './department-edit.component.scss',
})
export class DepartmentEditComponent {
  ref: DynamicDialogRef | undefined;

  form: FormGroup;

  constructor(
    private _departmentService: DepartmentService,
    private _formBuilder: FormBuilder,
    private _dynamicDialogConfig: DynamicDialogConfig,
    private _dynamicDialogRef: DynamicDialogRef
  ) {}

  ngOnInit(): void {
    const name = this._dynamicDialogConfig.data.data.name;

    this.form = this._formBuilder.group({
      name: [name ?? ''],
    });
  }

  onSubmit() {
    this._departmentService
      .edit(this._dynamicDialogConfig.data.data.id, this.form.value.name)
      .subscribe(() => {
        this._dynamicDialogRef?.close(true);
      });
  }
}
