import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../../navbar/navbar.component';

@Component({
  selector: 'layout-default',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './default.component.html',
  styleUrl: './default.component.scss',
})
export class LayoutDefaultComponent {}
