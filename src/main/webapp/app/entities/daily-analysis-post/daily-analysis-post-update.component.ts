import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDailyAnalysisPost, DailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';
import { DailyAnalysisPostService } from './daily-analysis-post.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';
import { IMt4Account } from 'app/shared/model/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/mt-4-account.service';

type SelectableEntity = IInstrument | IMt4Account;

@Component({
  selector: 'jhi-daily-analysis-post-update',
  templateUrl: './daily-analysis-post-update.component.html',
})
export class DailyAnalysisPostUpdateComponent implements OnInit {
  isSaving = false;
  instruments: IInstrument[] = [];
  mt4accounts: IMt4Account[] = [];

  editForm = this.fb.group({
    id: [],
    postTitle: [null, [Validators.required]],
    dateAdded: [null, [Validators.required]],
    dayOfWeek: [null, [Validators.required]],
    backgroundVolume: [null, [Validators.required]],
    priceAction: [null, [Validators.required]],
    reasonsToEnter: [],
    warningSigns: [],
    dailyChartImage: [],
    dailyChartImageContentType: [],
    oneHrChartImage: [],
    oneHrChartImageContentType: [],
    fiveMinChartImage: [],
    fiveMinChartImageContentType: [],
    planForToday: [],
    highVolBar: [],
    highVolBarLocation: [],
    instrument: [],
    mt4Account: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected dailyAnalysisPostService: DailyAnalysisPostService,
    protected instrumentService: InstrumentService,
    protected mt4AccountService: Mt4AccountService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyAnalysisPost }) => {
      if (!dailyAnalysisPost.id) {
        const today = moment().startOf('day');
        dailyAnalysisPost.dateAdded = today;
      }

      this.updateForm(dailyAnalysisPost);

      this.instrumentService
        .query({ filter: 'dailyanalysispost-is-null' })
        .pipe(
          map((res: HttpResponse<IInstrument[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInstrument[]) => {
          if (!dailyAnalysisPost.instrument || !dailyAnalysisPost.instrument.id) {
            this.instruments = resBody;
          } else {
            this.instrumentService
              .find(dailyAnalysisPost.instrument.id)
              .pipe(
                map((subRes: HttpResponse<IInstrument>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInstrument[]) => (this.instruments = concatRes));
          }
        });

      this.mt4AccountService.query().subscribe((res: HttpResponse<IMt4Account[]>) => (this.mt4accounts = res.body || []));
    });
  }

  updateForm(dailyAnalysisPost: IDailyAnalysisPost): void {
    this.editForm.patchValue({
      id: dailyAnalysisPost.id,
      postTitle: dailyAnalysisPost.postTitle,
      dateAdded: dailyAnalysisPost.dateAdded ? dailyAnalysisPost.dateAdded.format(DATE_TIME_FORMAT) : null,
      dayOfWeek: dailyAnalysisPost.dayOfWeek,
      backgroundVolume: dailyAnalysisPost.backgroundVolume,
      priceAction: dailyAnalysisPost.priceAction,
      reasonsToEnter: dailyAnalysisPost.reasonsToEnter,
      warningSigns: dailyAnalysisPost.warningSigns,
      dailyChartImage: dailyAnalysisPost.dailyChartImage,
      dailyChartImageContentType: dailyAnalysisPost.dailyChartImageContentType,
      oneHrChartImage: dailyAnalysisPost.oneHrChartImage,
      oneHrChartImageContentType: dailyAnalysisPost.oneHrChartImageContentType,
      fiveMinChartImage: dailyAnalysisPost.fiveMinChartImage,
      fiveMinChartImageContentType: dailyAnalysisPost.fiveMinChartImageContentType,
      planForToday: dailyAnalysisPost.planForToday,
      highVolBar: dailyAnalysisPost.highVolBar,
      highVolBarLocation: dailyAnalysisPost.highVolBarLocation,
      instrument: dailyAnalysisPost.instrument,
      mt4Account: dailyAnalysisPost.mt4Account,
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
    const dailyAnalysisPost = this.createFromForm();
    if (dailyAnalysisPost.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyAnalysisPostService.update(dailyAnalysisPost));
    } else {
      this.subscribeToSaveResponse(this.dailyAnalysisPostService.create(dailyAnalysisPost));
    }
  }

  private createFromForm(): IDailyAnalysisPost {
    return {
      ...new DailyAnalysisPost(),
      id: this.editForm.get(['id'])!.value,
      postTitle: this.editForm.get(['postTitle'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value ? moment(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      dayOfWeek: this.editForm.get(['dayOfWeek'])!.value,
      backgroundVolume: this.editForm.get(['backgroundVolume'])!.value,
      priceAction: this.editForm.get(['priceAction'])!.value,
      reasonsToEnter: this.editForm.get(['reasonsToEnter'])!.value,
      warningSigns: this.editForm.get(['warningSigns'])!.value,
      dailyChartImageContentType: this.editForm.get(['dailyChartImageContentType'])!.value,
      dailyChartImage: this.editForm.get(['dailyChartImage'])!.value,
      oneHrChartImageContentType: this.editForm.get(['oneHrChartImageContentType'])!.value,
      oneHrChartImage: this.editForm.get(['oneHrChartImage'])!.value,
      fiveMinChartImageContentType: this.editForm.get(['fiveMinChartImageContentType'])!.value,
      fiveMinChartImage: this.editForm.get(['fiveMinChartImage'])!.value,
      planForToday: this.editForm.get(['planForToday'])!.value,
      highVolBar: this.editForm.get(['highVolBar'])!.value,
      highVolBarLocation: this.editForm.get(['highVolBarLocation'])!.value,
      instrument: this.editForm.get(['instrument'])!.value,
      mt4Account: this.editForm.get(['mt4Account'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyAnalysisPost>>): void {
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
