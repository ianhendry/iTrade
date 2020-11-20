import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISignalSequences, SignalSequences } from 'app/shared/model/signal-sequences.model';
import { SignalSequencesService } from './signal-sequences.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-signal-sequences-update',
  templateUrl: './signal-sequences-update.component.html',
})
export class SignalSequencesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sequenceName: [null, [Validators.required]],
    sequenceIdentifier: [null, [Validators.required]],
    sequenceDescription: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected signalSequencesService: SignalSequencesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signalSequences }) => {
      this.updateForm(signalSequences);
    });
  }

  updateForm(signalSequences: ISignalSequences): void {
    this.editForm.patchValue({
      id: signalSequences.id,
      sequenceName: signalSequences.sequenceName,
      sequenceIdentifier: signalSequences.sequenceIdentifier,
      sequenceDescription: signalSequences.sequenceDescription,
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
    const signalSequences = this.createFromForm();
    if (signalSequences.id !== undefined) {
      this.subscribeToSaveResponse(this.signalSequencesService.update(signalSequences));
    } else {
      this.subscribeToSaveResponse(this.signalSequencesService.create(signalSequences));
    }
  }

  private createFromForm(): ISignalSequences {
    return {
      ...new SignalSequences(),
      id: this.editForm.get(['id'])!.value,
      sequenceName: this.editForm.get(['sequenceName'])!.value,
      sequenceIdentifier: this.editForm.get(['sequenceIdentifier'])!.value,
      sequenceDescription: this.editForm.get(['sequenceDescription'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISignalSequences>>): void {
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
