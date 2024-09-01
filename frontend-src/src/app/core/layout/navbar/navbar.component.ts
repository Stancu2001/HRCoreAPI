import { Component, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';
import { AuthService } from '../../../shared/services/auth.service';
import {
  NgForm,
  UntypedFormBuilder,
  UntypedFormGroup,
  Validators,
} from '@angular/forms';
import { finalize } from 'rxjs/operators';
import { pipe } from 'rxjs';
import { Router } from '@angular/router';
import { TokenService } from '../../../shared/services/token.service';

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
    private _tokenService: TokenService
  ) {
    this.loginForm = this._formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.isAuth = this._tokenService.isAuth();
  }

  showLogin() {
    this.visible = true;
  }

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.loginForm.disable();

    this._authService
      .login(this.loginForm.value.email!, this.loginForm.value.password!)
      .pipe(
        finalize(() => {
          this.loginForm.enable();
          this.loginNgForm.resetForm();
        })
      )
      .subscribe((res: any) => {
        this._tokenService.setToken(res.token);
        window.location.reload();
      });
  }

  logout() {
    this._tokenService.removeToken();
    window.location.reload();
  }
}
