import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISignalSequences } from 'app/shared/model/signal-sequences.model';

@Component({
  selector: 'jhi-signal-sequences-detail',
  templateUrl: './signal-sequences-detail.component.html',
})
export class SignalSequencesDetailComponent implements OnInit {
  signalSequences: ISignalSequences | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalSequences }) => (this.signalSequences = signalSequences));
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
