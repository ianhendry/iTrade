import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { KeyValuePairsComponent } from './key-value-pairs.component';
import { KeyValuePairsDetailComponent } from './key-value-pairs-detail.component';
import { KeyValuePairsUpdateComponent } from './key-value-pairs-update.component';
import { KeyValuePairsDeleteDialogComponent } from './key-value-pairs-delete-dialog.component';
import { keyValuePairsRoute } from './key-value-pairs.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(keyValuePairsRoute)],
  declarations: [KeyValuePairsComponent, KeyValuePairsDetailComponent, KeyValuePairsUpdateComponent, KeyValuePairsDeleteDialogComponent],
  entryComponents: [KeyValuePairsDeleteDialogComponent],
})
export class ITradeKeyValuePairsModule {}
