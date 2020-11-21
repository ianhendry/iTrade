import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISignalService, SignalService } from 'app/shared/model/signal-service.model';
import { SignalServiceService } from './signal-service.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITradeSignals } from 'app/shared/model/trade-signals.model';
import { TradeSignalsService } from 'app/entities/trade-signals/trade-signals.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';
import { IPriceDataHistory } from 'app/shared/model/price-data-history.model';
import { PriceDataHistoryService } from 'app/entities/price-data-history/price-data-history.service';

type SelectableEntity = ITradeSignals | IInstrument | IPriceDataHistory;

@Component({
  selector: 'jhi-signal-service-update',
  templateUrl: './signal-service-update.component.html',
})
export class SignalServiceUpdateComponent implements OnInit {
  isSaving = false;
  tradesignals: ITradeSignals[] = [];
  instruments: IInstrument[] = [];
  pricedatahistories: IPriceDataHistory[] = [];
  alertDateDp: any;

  editForm = this.fb.group({
    id: [],
    alertDate: [null, [Validators.required]],
    alertTime: [],
    ticker: [],
    alertText: [],
    alertDescription: [],
    signalIndicates: [],
    image: [],
    imageContentType: [],
    timeframe: [null, [Validators.required]],
    alertPrice: [],
    isSequencePresent: [],
    barVolume: [],
    barSize: [],
    barClose: [],
    isPublished: [],
    tradeSignals: [],
    intruments: [],
    priceDataHistory: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected signalServiceService: SignalServiceService,
    protected tradeSignalsService: TradeSignalsService,
    protected instrumentService: InstrumentService,
    protected priceDataHistoryService: PriceDataHistoryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalService }) => {
      if (!signalService.id) {
        const today = moment().startOf('day');
        signalService.alertTime = today;
      }

      this.updateForm(signalService);

      this.tradeSignalsService.query().subscribe((res: HttpResponse<ITradeSignals[]>) => (this.tradesignals = res.body || []));

      this.instrumentService.query().subscribe((res: HttpResponse<IInstrument[]>) => (this.instruments = res.body || []));

      this.priceDataHistoryService
        .query()
        .subscribe((res: HttpResponse<IPriceDataHistory[]>) => (this.pricedatahistories = res.body || []));
    });
  }

  updateForm(signalService: ISignalService): void {
    this.editForm.patchValue({
      id: signalService.id,
      alertDate: signalService.alertDate,
      alertTime: signalService.alertTime ? signalService.alertTime.format(DATE_TIME_FORMAT) : null,
      ticker: signalService.ticker,
      alertText: signalService.alertText,
      alertDescription: signalService.alertDescription,
      signalIndicates: signalService.signalIndicates,
      image: signalService.image,
      imageContentType: signalService.imageContentType,
      timeframe: signalService.timeframe,
      alertPrice: signalService.alertPrice,
      isSequencePresent: signalService.isSequencePresent,
      barVolume: signalService.barVolume,
      barSize: signalService.barSize,
      barClose: signalService.barClose,
      isPublished: signalService.isPublished,
      tradeSignals: signalService.tradeSignals,
      intruments: signalService.intruments,
      priceDataHistory: signalService.priceDataHistory,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const signalService = this.createFromForm();
    if (signalService.id !== undefined) {
      this.subscribeToSaveResponse(this.signalServiceService.update(signalService));
    } else {
      this.subscribeToSaveResponse(this.signalServiceService.create(signalService));
    }
  }

  private createFromForm(): ISignalService {
    return {
      ...new SignalService(),
      id: this.editForm.get(['id'])!.value,
      alertDate: this.editForm.get(['alertDate'])!.value,
      alertTime: this.editForm.get(['alertTime'])!.value ? moment(this.editForm.get(['alertTime'])!.value, DATE_TIME_FORMAT) : undefined,
      ticker: this.editForm.get(['ticker'])!.value,
      alertText: this.editForm.get(['alertText'])!.value,
      alertDescription: this.editForm.get(['alertDescription'])!.value,
      signalIndicates: this.editForm.get(['signalIndicates'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      timeframe: this.editForm.get(['timeframe'])!.value,
      alertPrice: this.editForm.get(['alertPrice'])!.value,
      isSequencePresent: this.editForm.get(['isSequencePresent'])!.value,
      barVolume: this.editForm.get(['barVolume'])!.value,
      barSize: this.editForm.get(['barSize'])!.value,
      barClose: this.editForm.get(['barClose'])!.value,
      isPublished: this.editForm.get(['isPublished'])!.value,
      tradeSignals: this.editForm.get(['tradeSignals'])!.value,
      intruments: this.editForm.get(['intruments'])!.value,
      priceDataHistory: this.editForm.get(['priceDataHistory'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISignalService>>): void {
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

  getSelected(selectedVals: IInstrument[], option: IInstrument): IInstrument {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
