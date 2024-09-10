import { NgModule } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { DialogModule } from 'primeng/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputGroupModule } from 'primeng/inputgroup';
import { StepperModule } from 'primeng/stepper';
import { DropdownModule } from 'primeng/dropdown';
import { SidebarModule } from 'primeng/sidebar';
import { TagModule } from 'primeng/tag';
import { NgxScannerQrcodeModule } from 'ngx-scanner-qrcode';
import { TooltipModule } from 'primeng/tooltip';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { ToastModule } from 'primeng/toast';
import { InputTextareaModule } from 'primeng/inputtextarea';

const primeNgModules = [
  InputTextModule,
  ButtonModule,
  DialogModule,
  ConfirmDialogModule,
  TabViewModule,
  TableModule,
  ToolbarModule,
  FloatLabelModule,
  InputGroupModule,
  StepperModule,
  DropdownModule,
  SidebarModule,
  TagModule,
  TooltipModule,
  OverlayPanelModule,
  ToastModule,
  InputTextareaModule,
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    RouterLinkActive,
    primeNgModules,
    NgxScannerQrcodeModule,
  ],
  exports: [
    CommonModule,
    NgIf,
    NgFor,
    primeNgModules,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    RouterLinkActive,
    NgxScannerQrcodeModule,
  ],
})
export class SharedModule {}
