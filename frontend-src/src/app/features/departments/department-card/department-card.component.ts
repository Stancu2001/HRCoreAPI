import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Department } from '../../../shared/models/department.types';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'department-card',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './department-card.component.html',
  styleUrl: './department-card.component.scss',
})
export class DepartmentCardComponent {
  @Input() department?: Department;

  @Output() onAdd = new EventEmitter();
  @Output() onEdit = new EventEmitter<number>();
  @Output() onDelete = new EventEmitter<number>();

  add() {
    this.onAdd.emit();
  }

  edit() {
    this.onEdit.emit(this.department!.id);
  }

  delete() {
    this.onDelete.emit(this.department!.id);
  }
}
