import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstrument, Instrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';

@Component({
  selector: 'jhi-instrument-update',
  templateUrl: './instrument-update.component.html',
})
export class InstrumentUpdateComponent implements OnInit {
  isSaving = false;
  dataFromDp: any;
  dateAddedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    dataProvider: [],
    ticker: [null, [Validators.required]],
    exchange: [],
    description: [],
    dataFrom: [],
    isActive: [],
    dateAdded: [],
    dateInactive: [],
  });

  constructor(protected instrumentService: InstrumentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instrument }) => {
      this.updateForm(instrument);
    });
  }

  updateForm(instrument: IInstrument): void {
    this.editForm.patchValue({
      id: instrument.id,
      dataProvider: instrument.dataProvider,
      ticker: instrument.ticker,
      exchange: instrument.exchange,
      description: instrument.description,
      dataFrom: instrument.dataFrom,
      isActive: instrument.isActive,
      dateAdded: instrument.dateAdded,
      dateInactive: instrument.dateInactive,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instrument = this.createFromForm();
    if (instrument.id !== undefined) {
      this.subscribeToSaveResponse(this.instrumentService.update(instrument));
    } else {
      this.subscribeToSaveResponse(this.instrumentService.create(instrument));
    }
  }

  private createFromForm(): IInstrument {
    return {
      ...new Instrument(),
      id: this.editForm.get(['id'])!.value,
      dataProvider: this.editForm.get(['dataProvider'])!.value,
      ticker: this.editForm.get(['ticker'])!.value,
      exchange: this.editForm.get(['exchange'])!.value,
      description: this.editForm.get(['description'])!.value,
      dataFrom: this.editForm.get(['dataFrom'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value,
      dateInactive: this.editForm.get(['dateInactive'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstrument>>): void {
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
}
