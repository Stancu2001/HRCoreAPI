import { Routes } from '@angular/router';
import { LayoutDefaultComponent } from './core/layout/layouts/default/default.component';
import { DepartmentsComponent } from './features/departments/departments.component';
import { QrComponent } from './features/qr/qr.component';

export const routes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/departments",
  },
  {
    path: '',
    component: LayoutDefaultComponent,
    children: [
      {
        path: 'departments',
        component: DepartmentsComponent,
      },
    ],
  },
  {
    path: 'qr',
    component: QrComponent,
  }
];
