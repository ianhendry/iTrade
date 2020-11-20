import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { Mt4AccountComponent } from './mt-4-account.component';
import { Mt4AccountDetailComponent } from './mt-4-account-detail.component';
import { Mt4AccountUpdateComponent } from './mt-4-account-update.component';
import { Mt4AccountDeleteDialogComponent } from './mt-4-account-delete-dialog.component';
import { mt4AccountRoute } from './mt-4-account.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(mt4AccountRoute)],
  declarations: [Mt4AccountComponent, Mt4AccountDetailComponent, Mt4AccountUpdateComponent, Mt4AccountDeleteDialogComponent],
  entryComponents: [Mt4AccountDeleteDialogComponent],
})
export class ITradeMt4AccountModule {}
