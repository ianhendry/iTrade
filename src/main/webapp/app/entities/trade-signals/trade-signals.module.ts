import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { TradeSignalsComponent } from './trade-signals.component';
import { TradeSignalsDetailComponent } from './trade-signals-detail.component';
import { TradeSignalsUpdateComponent } from './trade-signals-update.component';
import { TradeSignalsDeleteDialogComponent } from './trade-signals-delete-dialog.component';
import { tradeSignalsRoute } from './trade-signals.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(tradeSignalsRoute)],
  declarations: [TradeSignalsComponent, TradeSignalsDetailComponent, TradeSignalsUpdateComponent, TradeSignalsDeleteDialogComponent],
  entryComponents: [TradeSignalsDeleteDialogComponent],
})
export class ITradeTradeSignalsModule {}
