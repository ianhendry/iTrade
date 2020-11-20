import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { SignalSequencesComponent } from './signal-sequences.component';
import { SignalSequencesDetailComponent } from './signal-sequences-detail.component';
import { SignalSequencesUpdateComponent } from './signal-sequences-update.component';
import { SignalSequencesDeleteDialogComponent } from './signal-sequences-delete-dialog.component';
import { signalSequencesRoute } from './signal-sequences.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(signalSequencesRoute)],
  declarations: [
    SignalSequencesComponent,
    SignalSequencesDetailComponent,
    SignalSequencesUpdateComponent,
    SignalSequencesDeleteDialogComponent,
  ],
  entryComponents: [SignalSequencesDeleteDialogComponent],
})
export class ITradeSignalSequencesModule {}
