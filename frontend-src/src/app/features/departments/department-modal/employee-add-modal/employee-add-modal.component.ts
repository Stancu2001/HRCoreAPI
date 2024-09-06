import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  DynamicDialogConfig,
  DynamicDialogRef
} from 'primeng/dynamicdialog';
import { EmployeeService } from '../../../../shared/services/employee.service';
import { SharedModule } from '../../../../shared/shared.module';

@Component({
  selector: 'employee-add-modal',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './employee-add-modal.component.html',
  styleUrl: './employee-add-modal.component.scss',
})
export class EmployeeAddModalComponent implements OnInit {
  ref: DynamicDialogRef | undefined;

  form: FormGroup;
  departmentId: number;
  functions: Function[] = [];

  constructor(
    private _employeeService: EmployeeService,
    private _formBuilder: FormBuilder,
    private _dynamicDialogConfig: DynamicDialogConfig,
    private _dynamicDialogRef: DynamicDialogRef
  ) {}

  ngOnInit() {
    this.departmentId = this._dynamicDialogConfig.data.departmentId;
    this.functions = this._dynamicDialogConfig.data.functions;

    this.form = this._formBuilder.group({
      employee: this._formBuilder.group({
        firstName: [''],
        lastName: [''],
        email: ['', Validators.email],
        phone: [''],
        nationality: [''],
        cnp: [''],
        bankCode: [''],
        gender: [''],
        age: [0],
        registered: [''],
      }),
      address: this._formBuilder.group({
        street: [''],
        city: [''],
        country: [''],
        number: [''],
        apartment: [''],
        staircase: [''],
        floor: [''],
      }),
      function: this._formBuilder.group({
        id: [''],
      }),
    });
  }

  onSubmit() {
    this._employeeService.add(this.form.value).subscribe(() => {
      this._dynamicDialogRef?.close(true);
    });
  }
}
