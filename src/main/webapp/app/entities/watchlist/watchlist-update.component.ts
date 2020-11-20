import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWatchlist, Watchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';

@Component({
  selector: 'jhi-watchlist-update',
  templateUrl: './watchlist-update.component.html',
})
export class WatchlistUpdateComponent implements OnInit {
  isSaving = false;
  instruments: IInstrument[] = [];
  dateCreatedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    watchlistName: [null, [Validators.required]],
    watchlistDescription: [],
    dateCreated: [null, [Validators.required]],
    dateInactive: [],
    watchlistInactive: [],
    intruments: [],
  });

  constructor(
    protected watchlistService: WatchlistService,
    protected instrumentService: InstrumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ watchlist }) => {
      this.updateForm(watchlist);

      this.instrumentService.query().subscribe((res: HttpResponse<IInstrument[]>) => (this.instruments = res.body || []));
    });
  }

  updateForm(watchlist: IWatchlist): void {
    this.editForm.patchValue({
      id: watchlist.id,
      watchlistName: watchlist.watchlistName,
      watchlistDescription: watchlist.watchlistDescription,
      dateCreated: watchlist.dateCreated,
      dateInactive: watchlist.dateInactive,
      watchlistInactive: watchlist.watchlistInactive,
      intruments: watchlist.intruments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const watchlist = this.createFromForm();
    if (watchlist.id !== undefined) {
      this.subscribeToSaveResponse(this.watchlistService.update(watchlist));
    } else {
      this.subscribeToSaveResponse(this.watchlistService.create(watchlist));
    }
  }

  private createFromForm(): IWatchlist {
    return {
      ...new Watchlist(),
      id: this.editForm.get(['id'])!.value,
      watchlistName: this.editForm.get(['watchlistName'])!.value,
      watchlistDescription: this.editForm.get(['watchlistDescription'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateInactive: this.editForm.get(['dateInactive'])!.value,
      watchlistInactive: this.editForm.get(['watchlistInactive'])!.value,
      intruments: this.editForm.get(['intruments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWatchlist>>): void {
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
