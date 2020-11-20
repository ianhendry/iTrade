import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMt4Account, Mt4Account } from 'app/shared/model/mt-4-account.model';
import { Mt4AccountService } from './mt-4-account.service';
import { IMt4Trade } from 'app/shared/model/mt-4-trade.model';
import { Mt4TradeService } from 'app/entities/mt-4-trade/mt-4-trade.service';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from 'app/entities/watchlist/watchlist.service';

type SelectableEntity = IMt4Trade | IWatchlist;

@Component({
  selector: 'jhi-mt-4-account-update',
  templateUrl: './mt-4-account-update.component.html',
})
export class Mt4AccountUpdateComponent implements OnInit {
  isSaving = false;
  mt4trades: IMt4Trade[] = [];
  watchlists: IWatchlist[] = [];
  accountCloseDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountType: [],
    accountBroker: [],
    accountLogin: [],
    accountPassword: [],
    accountActive: [],
    accountCloseDate: [],
    mt4Trade: [],
    watchlist: [],
  });

  constructor(
    protected mt4AccountService: Mt4AccountService,
    protected mt4TradeService: Mt4TradeService,
    protected watchlistService: WatchlistService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mt4Account }) => {
      this.updateForm(mt4Account);

      this.mt4TradeService.query().subscribe((res: HttpResponse<IMt4Trade[]>) => (this.mt4trades = res.body || []));

      this.watchlistService.query().subscribe((res: HttpResponse<IWatchlist[]>) => (this.watchlists = res.body || []));
    });
  }

  updateForm(mt4Account: IMt4Account): void {
    this.editForm.patchValue({
      id: mt4Account.id,
      accountType: mt4Account.accountType,
      accountBroker: mt4Account.accountBroker,
      accountLogin: mt4Account.accountLogin,
      accountPassword: mt4Account.accountPassword,
      accountActive: mt4Account.accountActive,
      accountCloseDate: mt4Account.accountCloseDate,
      mt4Trade: mt4Account.mt4Trade,
      watchlist: mt4Account.watchlist,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mt4Account = this.createFromForm();
    if (mt4Account.id !== undefined) {
      this.subscribeToSaveResponse(this.mt4AccountService.update(mt4Account));
    } else {
      this.subscribeToSaveResponse(this.mt4AccountService.create(mt4Account));
    }
  }

  private createFromForm(): IMt4Account {
    return {
      ...new Mt4Account(),
      id: this.editForm.get(['id'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      accountBroker: this.editForm.get(['accountBroker'])!.value,
      accountLogin: this.editForm.get(['accountLogin'])!.value,
      accountPassword: this.editForm.get(['accountPassword'])!.value,
      accountActive: this.editForm.get(['accountActive'])!.value,
      accountCloseDate: this.editForm.get(['accountCloseDate'])!.value,
      mt4Trade: this.editForm.get(['mt4Trade'])!.value,
      watchlist: this.editForm.get(['watchlist'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMt4Account>>): void {
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
