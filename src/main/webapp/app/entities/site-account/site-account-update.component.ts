import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISiteAccount, SiteAccount } from 'app/shared/model/site-account.model';
import { SiteAccountService } from './site-account.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMt4Account } from 'app/shared/model/mt-4-account.model';
import { Mt4AccountService } from 'app/entities/mt-4-account/mt-4-account.service';

type SelectableEntity = IUser | IMt4Account;

@Component({
  selector: 'jhi-site-account-update',
  templateUrl: './site-account-update.component.html',
})
export class SiteAccountUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  mt4accounts: IMt4Account[] = [];
  accountOpenDateDp: any;
  accountCloseDateDp: any;

  editForm = this.fb.group({
    id: [],
    accountEmail: [],
    accountName: [],
    accountReal: [],
    accountBalance: [],
    accountOpenDate: [],
    accountCloseDate: [],
    user: [],
    mt4Account: [],
  });

  constructor(
    protected siteAccountService: SiteAccountService,
    protected userService: UserService,
    protected mt4AccountService: Mt4AccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteAccount }) => {
      this.updateForm(siteAccount);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.mt4AccountService.query().subscribe((res: HttpResponse<IMt4Account[]>) => (this.mt4accounts = res.body || []));
    });
  }

  updateForm(siteAccount: ISiteAccount): void {
    this.editForm.patchValue({
      id: siteAccount.id,
      accountEmail: siteAccount.accountEmail,
      accountName: siteAccount.accountName,
      accountReal: siteAccount.accountReal,
      accountBalance: siteAccount.accountBalance,
      accountOpenDate: siteAccount.accountOpenDate,
      accountCloseDate: siteAccount.accountCloseDate,
      user: siteAccount.user,
      mt4Account: siteAccount.mt4Account,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siteAccount = this.createFromForm();
    if (siteAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.siteAccountService.update(siteAccount));
    } else {
      this.subscribeToSaveResponse(this.siteAccountService.create(siteAccount));
    }
  }

  private createFromForm(): ISiteAccount {
    return {
      ...new SiteAccount(),
      id: this.editForm.get(['id'])!.value,
      accountEmail: this.editForm.get(['accountEmail'])!.value,
      accountName: this.editForm.get(['accountName'])!.value,
      accountReal: this.editForm.get(['accountReal'])!.value,
      accountBalance: this.editForm.get(['accountBalance'])!.value,
      accountOpenDate: this.editForm.get(['accountOpenDate'])!.value,
      accountCloseDate: this.editForm.get(['accountCloseDate'])!.value,
      user: this.editForm.get(['user'])!.value,
      mt4Account: this.editForm.get(['mt4Account'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiteAccount>>): void {
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
