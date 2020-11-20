import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPriceDataHistory, PriceDataHistory } from 'app/shared/model/price-data-history.model';
import { PriceDataHistoryService } from './price-data-history.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';

@Component({
  selector: 'jhi-price-data-history-update',
  templateUrl: './price-data-history-update.component.html',
})
export class PriceDataHistoryUpdateComponent implements OnInit {
  isSaving = false;
  instruments: IInstrument[] = [];
  priceDateDp: any;

  editForm = this.fb.group({
    id: [],
    priceTimeframe: [null, [Validators.required]],
    priceDate: [],
    priceTime: [],
    priceOpen: [],
    priceHigh: [],
    priceLow: [],
    priceClose: [],
    priceVolume: [],
    instrument: [],
  });

  constructor(
    protected priceDataHistoryService: PriceDataHistoryService,
    protected instrumentService: InstrumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priceDataHistory }) => {
      if (!priceDataHistory.id) {
        const today = moment().startOf('day');
        priceDataHistory.priceTime = today;
      }

      this.updateForm(priceDataHistory);

      this.instrumentService.query().subscribe((res: HttpResponse<IInstrument[]>) => (this.instruments = res.body || []));
    });
  }

  updateForm(priceDataHistory: IPriceDataHistory): void {
    this.editForm.patchValue({
      id: priceDataHistory.id,
      priceTimeframe: priceDataHistory.priceTimeframe,
      priceDate: priceDataHistory.priceDate,
      priceTime: priceDataHistory.priceTime ? priceDataHistory.priceTime.format(DATE_TIME_FORMAT) : null,
      priceOpen: priceDataHistory.priceOpen,
      priceHigh: priceDataHistory.priceHigh,
      priceLow: priceDataHistory.priceLow,
      priceClose: priceDataHistory.priceClose,
      priceVolume: priceDataHistory.priceVolume,
      instrument: priceDataHistory.instrument,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priceDataHistory = this.createFromForm();
    if (priceDataHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.priceDataHistoryService.update(priceDataHistory));
    } else {
      this.subscribeToSaveResponse(this.priceDataHistoryService.create(priceDataHistory));
    }
  }

  private createFromForm(): IPriceDataHistory {
    return {
      ...new PriceDataHistory(),
      id: this.editForm.get(['id'])!.value,
      priceTimeframe: this.editForm.get(['priceTimeframe'])!.value,
      priceDate: this.editForm.get(['priceDate'])!.value,
      priceTime: this.editForm.get(['priceTime'])!.value ? moment(this.editForm.get(['priceTime'])!.value, DATE_TIME_FORMAT) : undefined,
      priceOpen: this.editForm.get(['priceOpen'])!.value,
      priceHigh: this.editForm.get(['priceHigh'])!.value,
      priceLow: this.editForm.get(['priceLow'])!.value,
      priceClose: this.editForm.get(['priceClose'])!.value,
      priceVolume: this.editForm.get(['priceVolume'])!.value,
      instrument: this.editForm.get(['instrument'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriceDataHistory>>): void {
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

  trackById(index: number, item: IInstrument): any {
    return item.id;
  }
}
