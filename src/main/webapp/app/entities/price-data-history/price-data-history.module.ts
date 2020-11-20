import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { PriceDataHistoryComponent } from './price-data-history.component';
import { PriceDataHistoryDetailComponent } from './price-data-history-detail.component';
import { PriceDataHistoryUpdateComponent } from './price-data-history-update.component';
import { PriceDataHistoryDeleteDialogComponent } from './price-data-history-delete-dialog.component';
import { priceDataHistoryRoute } from './price-data-history.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(priceDataHistoryRoute)],
  declarations: [
    PriceDataHistoryComponent,
    PriceDataHistoryDetailComponent,
    PriceDataHistoryUpdateComponent,
    PriceDataHistoryDeleteDialogComponent,
  ],
  entryComponents: [PriceDataHistoryDeleteDialogComponent],
})
export class ITradePriceDataHistoryModule {}
