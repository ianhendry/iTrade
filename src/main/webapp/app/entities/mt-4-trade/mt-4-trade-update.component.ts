import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMt4Trade, Mt4Trade } from 'app/shared/model/mt-4-trade.model';
import { Mt4TradeService } from './mt-4-trade.service';
import { ITradeJournalPost } from 'app/shared/model/trade-journal-post.model';
import { TradeJournalPostService } from 'app/entities/trade-journal-post/trade-journal-post.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';

type SelectableEntity = ITradeJournalPost | IInstrument;

@Component({
  selector: 'jhi-mt-4-trade-update',
  templateUrl: './mt-4-trade-update.component.html',
})
export class Mt4TradeUpdateComponent implements OnInit {
  isSaving = false;
  tradejournalposts: ITradeJournalPost[] = [];
  instruments: IInstrument[] = [];

  editForm = this.fb.group({
    id: [],
    ticket: [null, [Validators.required]],
    openTime: [],
    directionType: [],
    positionSize: [],
    openPrice: [],
    stopLossPrice: [],
    takeProfitPrice: [],
    closeTime: [],
    closePrice: [],
    commission: [],
    taxes: [],
    swap: [],
    profit: [],
    tradeJournalPost: [],
    instrument: [],
  });

  constructor(
    protected mt4TradeService: Mt4TradeService,
    protected tradeJournalPostService: TradeJournalPostService,
    protected instrumentService: InstrumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mt4Trade }) => {
      if (!mt4Trade.id) {
        const today = moment().startOf('day');
        mt4Trade.openTime = today;
        mt4Trade.closeTime = today;
      }

      this.updateForm(mt4Trade);

      this.tradeJournalPostService
        .query({ filter: 'mt4trade-is-null' })
        .pipe(
          map((res: HttpResponse<ITradeJournalPost[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITradeJournalPost[]) => {
          if (!mt4Trade.tradeJournalPost || !mt4Trade.tradeJournalPost.id) {
            this.tradejournalposts = resBody;
          } else {
            this.tradeJournalPostService
              .find(mt4Trade.tradeJournalPost.id)
              .pipe(
                map((subRes: HttpResponse<ITradeJournalPost>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITradeJournalPost[]) => (this.tradejournalposts = concatRes));
          }
        });

      this.instrumentService
        .query({ filter: 'mt4trade-is-null' })
        .pipe(
          map((res: HttpResponse<IInstrument[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInstrument[]) => {
          if (!mt4Trade.instrument || !mt4Trade.instrument.id) {
            this.instruments = resBody;
          } else {
            this.instrumentService
              .find(mt4Trade.instrument.id)
              .pipe(
                map((subRes: HttpResponse<IInstrument>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInstrument[]) => (this.instruments = concatRes));
          }
        });
    });
  }

  updateForm(mt4Trade: IMt4Trade): void {
    this.editForm.patchValue({
      id: mt4Trade.id,
      ticket: mt4Trade.ticket,
      openTime: mt4Trade.openTime ? mt4Trade.openTime.format(DATE_TIME_FORMAT) : null,
      directionType: mt4Trade.directionType,
      positionSize: mt4Trade.positionSize,
      openPrice: mt4Trade.openPrice,
      stopLossPrice: mt4Trade.stopLossPrice,
      takeProfitPrice: mt4Trade.takeProfitPrice,
      closeTime: mt4Trade.closeTime ? mt4Trade.closeTime.format(DATE_TIME_FORMAT) : null,
      closePrice: mt4Trade.closePrice,
      commission: mt4Trade.commission,
      taxes: mt4Trade.taxes,
      swap: mt4Trade.swap,
      profit: mt4Trade.profit,
      tradeJournalPost: mt4Trade.tradeJournalPost,
      instrument: mt4Trade.instrument,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mt4Trade = this.createFromForm();
    if (mt4Trade.id !== undefined) {
      this.subscribeToSaveResponse(this.mt4TradeService.update(mt4Trade));
    } else {
      this.subscribeToSaveResponse(this.mt4TradeService.create(mt4Trade));
    }
  }

  private createFromForm(): IMt4Trade {
    return {
      ...new Mt4Trade(),
      id: this.editForm.get(['id'])!.value,
      ticket: this.editForm.get(['ticket'])!.value,
      openTime: this.editForm.get(['openTime'])!.value ? moment(this.editForm.get(['openTime'])!.value, DATE_TIME_FORMAT) : undefined,
      directionType: this.editForm.get(['directionType'])!.value,
      positionSize: this.editForm.get(['positionSize'])!.value,
      openPrice: this.editForm.get(['openPrice'])!.value,
      stopLossPrice: this.editForm.get(['stopLossPrice'])!.value,
      takeProfitPrice: this.editForm.get(['takeProfitPrice'])!.value,
      closeTime: this.editForm.get(['closeTime'])!.value ? moment(this.editForm.get(['closeTime'])!.value, DATE_TIME_FORMAT) : undefined,
      closePrice: this.editForm.get(['closePrice'])!.value,
      commission: this.editForm.get(['commission'])!.value,
      taxes: this.editForm.get(['taxes'])!.value,
      swap: this.editForm.get(['swap'])!.value,
      profit: this.editForm.get(['profit'])!.value,
      tradeJournalPost: this.editForm.get(['tradeJournalPost'])!.value,
      instrument: this.editForm.get(['instrument'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMt4Trade>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
