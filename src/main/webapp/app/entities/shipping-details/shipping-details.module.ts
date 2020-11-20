import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { ShippingDetailsComponent } from './shipping-details.component';
import { ShippingDetailsDetailComponent } from './shipping-details-detail.component';
import { ShippingDetailsUpdateComponent } from './shipping-details-update.component';
import { ShippingDetailsDeleteDialogComponent } from './shipping-details-delete-dialog.component';
import { shippingDetailsRoute } from './shipping-details.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(shippingDetailsRoute)],
  declarations: [
    ShippingDetailsComponent,
    ShippingDetailsDetailComponent,
    ShippingDetailsUpdateComponent,
    ShippingDetailsDeleteDialogComponent,
  ],
  entryComponents: [ShippingDetailsDeleteDialogComponent],
})
export class ITradeShippingDetailsModule {}
