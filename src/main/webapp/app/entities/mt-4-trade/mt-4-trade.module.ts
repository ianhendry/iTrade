import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { Mt4TradeComponent } from './mt-4-trade.component';
import { Mt4TradeDetailComponent } from './mt-4-trade-detail.component';
import { Mt4TradeUpdateComponent } from './mt-4-trade-update.component';
import { Mt4TradeDeleteDialogComponent } from './mt-4-trade-delete-dialog.component';
import { mt4TradeRoute } from './mt-4-trade.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(mt4TradeRoute)],
  declarations: [Mt4TradeComponent, Mt4TradeDetailComponent, Mt4TradeUpdateComponent, Mt4TradeDeleteDialogComponent],
  entryComponents: [Mt4TradeDeleteDialogComponent],
})
export class ITradeMt4TradeModule {}
