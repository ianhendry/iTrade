import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITradeSignals, TradeSignals } from 'app/shared/model/trade-signals.model';
import { TradeSignalsService } from './trade-signals.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISignalSequences } from 'app/shared/model/signal-sequences.model';
import { SignalSequencesService } from 'app/entities/signal-sequences/signal-sequences.service';

@Component({
  selector: 'jhi-trade-signals-update',
  templateUrl: './trade-signals-update.component.html',
})
export class TradeSignalsUpdateComponent implements OnInit {
  isSaving = false;
  signalsequences: ISignalSequences[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    note: [],
    numberOfBars: [],
    signalIndicates: [],
    description: [],
    background: [],
    actionToTake: [],
    sequenceNumber: [],
    signalsSequences: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tradeSignalsService: TradeSignalsService,
    protected signalSequencesService: SignalSequencesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradeSignals }) => {
      this.updateForm(tradeSignals);

      this.signalSequencesService.query().subscribe((res: HttpResponse<ISignalSequences[]>) => (this.signalsequences = res.body || []));
    });
  }

  updateForm(tradeSignals: ITradeSignals): void {
    this.editForm.patchValue({
      id: tradeSignals.id,
      name: tradeSignals.name,
      note: tradeSignals.note,
      numberOfBars: tradeSignals.numberOfBars,
      signalIndicates: tradeSignals.signalIndicates,
      description: tradeSignals.description,
      background: tradeSignals.background,
      actionToTake: tradeSignals.actionToTake,
      sequenceNumber: tradeSignals.sequenceNumber,
      signalsSequences: tradeSignals.signalsSequences,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('iTradeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tradeSignals = this.createFromForm();
    if (tradeSignals.id !== undefined) {
      this.subscribeToSaveResponse(this.tradeSignalsService.update(tradeSignals));
    } else {
      this.subscribeToSaveResponse(this.tradeSignalsService.create(tradeSignals));
    }
  }

  private createFromForm(): ITradeSignals {
    return {
      ...new TradeSignals(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      note: this.editForm.get(['note'])!.value,
      numberOfBars: this.editForm.get(['numberOfBars'])!.value,
      signalIndicates: this.editForm.get(['signalIndicates'])!.value,
      description: this.editForm.get(['description'])!.value,
      background: this.editForm.get(['background'])!.value,
      actionToTake: this.editForm.get(['actionToTake'])!.value,
      sequenceNumber: this.editForm.get(['sequenceNumber'])!.value,
      signalsSequences: this.editForm.get(['signalsSequences'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradeSignals>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISignalSequences): any {
    return item.id;
  }
}
