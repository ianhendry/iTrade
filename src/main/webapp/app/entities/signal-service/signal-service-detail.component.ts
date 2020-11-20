import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISignalService } from 'app/shared/model/signal-service.model';

@Component({
  selector: 'jhi-signal-service-detail',
  templateUrl: './signal-service-detail.component.html',
})
export class SignalServiceDetailComponent implements OnInit {
  signalService: ISignalService | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalService }) => (this.signalService = signalService));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
