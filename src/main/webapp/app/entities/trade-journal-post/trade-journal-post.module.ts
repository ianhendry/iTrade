import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { TradeJournalPostComponent } from './trade-journal-post.component';
import { TradeJournalPostDetailComponent } from './trade-journal-post-detail.component';
import { TradeJournalPostUpdateComponent } from './trade-journal-post-update.component';
import { TradeJournalPostDeleteDialogComponent } from './trade-journal-post-delete-dialog.component';
import { tradeJournalPostRoute } from './trade-journal-post.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(tradeJournalPostRoute)],
  declarations: [
    TradeJournalPostComponent,
    TradeJournalPostDetailComponent,
    TradeJournalPostUpdateComponent,
    TradeJournalPostDeleteDialogComponent,
  ],
  entryComponents: [TradeJournalPostDeleteDialogComponent],
})
export class ITradeTradeJournalPostModule {}
