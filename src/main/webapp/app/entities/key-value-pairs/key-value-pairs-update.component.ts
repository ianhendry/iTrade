import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKeyValuePairs, KeyValuePairs } from 'app/shared/model/key-value-pairs.model';
import { KeyValuePairsService } from './key-value-pairs.service';

@Component({
  selector: 'jhi-key-value-pairs-update',
  templateUrl: './key-value-pairs-update.component.html',
})
export class KeyValuePairsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    keyValueGroup: [],
    keyValue: [],
    keyValueEntry: [],
  });

  constructor(protected keyValuePairsService: KeyValuePairsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ keyValuePairs }) => {
      this.updateForm(keyValuePairs);
    });
  }

  updateForm(keyValuePairs: IKeyValuePairs): void {
    this.editForm.patchValue({
      id: keyValuePairs.id,
      keyValueGroup: keyValuePairs.keyValueGroup,
      keyValue: keyValuePairs.keyValue,
      keyValueEntry: keyValuePairs.keyValueEntry,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const keyValuePairs = this.createFromForm();
    if (keyValuePairs.id !== undefined) {
      this.subscribeToSaveResponse(this.keyValuePairsService.update(keyValuePairs));
    } else {
      this.subscribeToSaveResponse(this.keyValuePairsService.create(keyValuePairs));
    }
  }

  private createFromForm(): IKeyValuePairs {
    return {
      ...new KeyValuePairs(),
      id: this.editForm.get(['id'])!.value,
      keyValueGroup: this.editForm.get(['keyValueGroup'])!.value,
      keyValue: this.editForm.get(['keyValue'])!.value,
      keyValueEntry: this.editForm.get(['keyValueEntry'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKeyValuePairs>>): void {
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
