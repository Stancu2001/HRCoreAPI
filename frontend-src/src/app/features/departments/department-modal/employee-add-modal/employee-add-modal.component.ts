import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { EmployeeService } from '../../../../shared/services/employee.service';
import { SharedModule } from '../../../../shared/shared.module';
import { MessageService } from 'primeng/api';

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
  isEdit: boolean;
  departmentId: number;
  employeeId: number;
  functions: Function[] = [];

  constructor(
    private _messageService: MessageService,
    private _employeeService: EmployeeService,
    private _formBuilder: FormBuilder,
    private _dynamicDialogConfig: DynamicDialogConfig,
    private _dynamicDialogRef: DynamicDialogRef
  ) {}

  ngOnInit() {
    const employee = this._dynamicDialogConfig.data.employee;

    this.departmentId = this._dynamicDialogConfig.data.departmentId;
    this.functions = this._dynamicDialogConfig.data.functions;
    this.isEdit = this._dynamicDialogConfig.data.edit;
    this.employeeId = employee?.idEmployee;

    this.form = this._formBuilder.group({
      employee: this._formBuilder.group({
        firstName: new FormControl(employee?.firstName ?? '', [Validators.required]),
        lastName: new FormControl(employee?.lastName ?? '', [Validators.required]),
        email: [employee?.email ?? '', Validators.email],
        phone: new FormControl(employee?.phone ?? '', [Validators.required]),
        nationality: new FormControl(employee?.nationality ?? '', [Validators.required]),
        cnp: new FormControl(employee?.cnp ?? '', [Validators.required]),
        bankCode: new FormControl(employee?.bankCode ?? '', [Validators.required]),
        gender: new FormControl(employee?.gender ?? '', [Validators.required]),
        age: new FormControl(employee?.age ?? '', [Validators.min(1), Validators.max(99)]),
        remarks: new FormControl(employee?.remarks ?? '', [Validators.required]),
      }),
      address: this._formBuilder.group({
        street: new FormControl(employee?.address.street ?? '', [Validators.required]),
        city: new FormControl(employee?.address.city ?? '', [Validators.required]),
        country: new FormControl(employee?.address.country ?? '', [Validators.required]),
        number: new FormControl(employee?.address.number ?? '', [Validators.required]),
        apartment: new FormControl(employee?.address.apartment ?? '', [Validators.required]),
        staircase: new FormControl(employee?.address.staircase ?? '', [Validators.required]),
        floor: new FormControl(employee?.address.floor ?? '', [Validators.required, Validators.min(1)]),
      }),
      function: this._formBuilder.group({
        id: new FormControl(employee?.departmentalFunctions.functionId ?? '', [Validators.required]),
      }),
    });
  }

  onSubmit() {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      this._messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Please fill in all the required fields',
      });

      return;
    }

    if (this.isEdit) {
      this.form.value.functionId = this._dynamicDialogConfig.data.function?.functionId;

      this._employeeService.edit(this.employeeId, this.form.value).subscribe((r) => {
        this._dynamicDialogRef?.close(true);

        this._messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Employee edited successfully',
        });
      });
    } else {
      this._employeeService.add(this.form.value).subscribe(() => {
        this._dynamicDialogRef?.close(true);

        this._messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Employee added successfully',
        });
      });
    }
  }
}
