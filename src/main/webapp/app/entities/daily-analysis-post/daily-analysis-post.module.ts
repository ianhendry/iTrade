import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { DailyAnalysisPostComponent } from './daily-analysis-post.component';
import { DailyAnalysisPostDetailComponent } from './daily-analysis-post-detail.component';
import { DailyAnalysisPostUpdateComponent } from './daily-analysis-post-update.component';
import { DailyAnalysisPostDeleteDialogComponent } from './daily-analysis-post-delete-dialog.component';
import { dailyAnalysisPostRoute } from './daily-analysis-post.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(dailyAnalysisPostRoute)],
  declarations: [
    DailyAnalysisPostComponent,
    DailyAnalysisPostDetailComponent,
    DailyAnalysisPostUpdateComponent,
    DailyAnalysisPostDeleteDialogComponent,
  ],
  entryComponents: [DailyAnalysisPostDeleteDialogComponent],
})
export class ITradeDailyAnalysisPostModule {}
