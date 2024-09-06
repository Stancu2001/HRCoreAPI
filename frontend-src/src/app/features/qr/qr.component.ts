import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { NgxScannerQrcodeComponent } from 'ngx-scanner-qrcode';
import { SharedModule } from '../../shared/shared.module';
import { QrService } from '../../shared/services/qr.service';

@Component({
  selector: 'app-qr',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './qr.component.html',
  styleUrl: './qr.component.scss',
})
export class QrComponent implements AfterViewInit {
  @ViewChild(NgxScannerQrcodeComponent) scanner: NgxScannerQrcodeComponent;

  constructor(private _qrService: QrService) {}

  ngAfterViewInit(): void {
    this.scanner.start();

    this.scanner.event.subscribe(event => {
      this._qrService.send(event[0].value).subscribe((data) => {
        console.log(data);
      });

      this.scanner.stop();
      setTimeout(() => {
        this.scanner.start();
      }, 2500);
    });
  }
}
