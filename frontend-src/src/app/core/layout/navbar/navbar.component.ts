import { Component, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { AuthService } from '../../../shared/services/auth.service';
import {
  FormControl,
  NgForm,
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { finalize } from 'rxjs/operators';
import { pipe } from 'rxjs';
import { Router } from '@angular/router';
import { TokenService } from '../../../shared/services/token.service';
import { MessageService } from 'primeng/api';
import { PayrollService } from '../../../shared/services/payroll.service';

interface NavigationItem {
  name: string;
  path: string;
}

@Component({
  selector: 'navbar',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {
  items: NavigationItem[] = [
    {
      name: 'Departments',
      path: '/departments',
    },
  ];

  @ViewChild('signInNgForm') loginNgForm: NgForm;
  visible: boolean = false;
  loginForm: UntypedFormGroup;

  isAuth: boolean = false;

  constructor(
    private _authService: AuthService,
    private _formBuilder: UntypedFormBuilder,
    private _tokenService: TokenService,
    private _payrollService: PayrollService,
    private _messageService: MessageService
  ) {
    this.loginForm = this._formBuilder.group({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit() {
    this.isAuth = this._tokenService.isAuth();

    this.loginForm.markAllAsTouched();
    this.loginForm.markAsDirty();
    this.loginForm.markAsTouched();
    this.loginForm.markAsPristine();
  }

  showLogin() {
    this.visible = true;
  }

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loginForm.disable();

    // this._authService
    //   .login(this.loginForm.value.email!, this.loginForm.value.password!)
    //   .pipe(
    //     finalize(() => {
    //       this.loginForm.enable();
    //       this.loginNgForm.resetForm();
    //     })
    //   )
    //   .subscribe((res: any) => {
    //     this._tokenService.setToken(res.token);
    //     window.location.reload();
    //   });

    this._authService
      .login(this.loginForm.value.email!, this.loginForm.value.password!)
      .subscribe({
        next: (res: any) => {
          this._tokenService.setToken(res.token);
          window.location.reload();
        },
        error: err => {
          this._messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Invalid email or password',
          });
        },
      });
  }

  logout() {
    this._tokenService.removeToken();
    window.location.reload();
  }

  calculateAllSalaries() {
    this._payrollService.calculate().subscribe(() => {
      this._messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: 'Salaries calculated successfully',
      });
    });
  }
}
