import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { SignalServiceComponent } from './signal-service.component';
import { SignalServiceDetailComponent } from './signal-service-detail.component';
import { SignalServiceUpdateComponent } from './signal-service-update.component';
import { SignalServiceDeleteDialogComponent } from './signal-service-delete-dialog.component';
import { signalServiceRoute } from './signal-service.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(signalServiceRoute)],
  declarations: [SignalServiceComponent, SignalServiceDetailComponent, SignalServiceUpdateComponent, SignalServiceDeleteDialogComponent],
  entryComponents: [SignalServiceDeleteDialogComponent],
})
export class ITradeSignalServiceModule {}
