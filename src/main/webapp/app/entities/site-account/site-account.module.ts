import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { SiteAccountComponent } from './site-account.component';
import { SiteAccountDetailComponent } from './site-account-detail.component';
import { SiteAccountUpdateComponent } from './site-account-update.component';
import { SiteAccountDeleteDialogComponent } from './site-account-delete-dialog.component';
import { siteAccountRoute } from './site-account.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(siteAccountRoute)],
  declarations: [SiteAccountComponent, SiteAccountDetailComponent, SiteAccountUpdateComponent, SiteAccountDeleteDialogComponent],
  entryComponents: [SiteAccountDeleteDialogComponent],
})
export class ITradeSiteAccountModule {}
