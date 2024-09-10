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
  departmentId: number;
  functions: Function[] = [];

  constructor(
    private _messageService: MessageService,
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
        firstName: new FormControl('', [Validators.required]),
        lastName: new FormControl('', [Validators.required]),
        email: ['', Validators.email],
        phone: new FormControl('', [Validators.required]),
        nationality: new FormControl('', [Validators.required]),
        cnp: new FormControl('', [Validators.required]),
        bankCode: new FormControl('', [Validators.required]),
        gender: new FormControl('', [Validators.required]),
        age: new FormControl('', [Validators.min(1), Validators.max(99)]),
        remarks: new FormControl('', [Validators.required]),
      }),
      address: this._formBuilder.group({
        street: new FormControl('', [Validators.required]),
        city: new FormControl('', [Validators.required]),
        country: new FormControl('', [Validators.required]),
        number: new FormControl('', [Validators.required]),
        apartment: new FormControl('', [Validators.required]),
        staircase: new FormControl('', [Validators.required]),
        floor: new FormControl('', [Validators.required, Validators.min(1)]),
      }),
      function: this._formBuilder.group({
        id: new FormControl('', [Validators.required]),
      }),
    });
  }

  onSubmit() {
    this.form.markAllAsTouched();

    console.log(this.form);
    console.log(this.form.get('employee')?.errors);
    console.log(this.form.invalid);
    
    if (this.form.invalid) {
      this._messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: 'Please fill in all the required fields',
      });

      return;
    }

    this._employeeService.add(this.form.value).subscribe(() => {
      this._dynamicDialogRef?.close(true);
    });
  }
}
