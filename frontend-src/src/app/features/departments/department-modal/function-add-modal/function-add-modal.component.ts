import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FunctionService } from '../../../../shared/services/function.service';
import { SharedModule } from '../../../../shared/shared.module';

@Component({
  selector: 'function-add-modal',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './function-add-modal.component.html',
  styleUrl: './function-add-modal.component.scss',
})
export class FunctionAddModalComponent implements OnInit {
  ref: DynamicDialogRef | undefined;

  form: FormGroup;
  isEdit: boolean;
  departmentId: number;

  constructor(
    private _functionService: FunctionService,
    private _formBuilder: FormBuilder,
    private _dynamicDialogConfig: DynamicDialogConfig,
    private _dynamicDialogRef: DynamicDialogRef
  ) {}

  ngOnInit(): void {
    this.departmentId = this._dynamicDialogConfig.data.departmentId;
    this.isEdit = this._dynamicDialogConfig.data.edit;

    const funct = this._dynamicDialogConfig.data.function;

    this.form = this._formBuilder.group({
      name: [funct?.functionName ?? ''],
    });
  }

  onSubmit() {
    if (this.isEdit) {
      this.form.value.functionId = this._dynamicDialogConfig.data.function?.functionId;

      this._functionService.edit(this.form.value).subscribe(() => {
        this._dynamicDialogRef?.close(true);
      });
    } else {
      this._functionService.add(this.departmentId, this.form.value).subscribe(() => {
        this._dynamicDialogRef?.close(true);
      });
    }
  }
}
