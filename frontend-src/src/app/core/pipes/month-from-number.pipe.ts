import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'monthFromNumber',
  standalone: true,
})
export class MonthFromNumberPipe implements PipeTransform {
  transform(value: number): string {
    const date = new Date();
    date.setMonth(value - 1); 
    return date.toLocaleString('default', { month: 'long' });
  }
}
