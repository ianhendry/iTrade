import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { WatchlistComponent } from './watchlist.component';
import { WatchlistDetailComponent } from './watchlist-detail.component';
import { WatchlistUpdateComponent } from './watchlist-update.component';
import { WatchlistDeleteDialogComponent } from './watchlist-delete-dialog.component';
import { watchlistRoute } from './watchlist.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(watchlistRoute)],
  declarations: [WatchlistComponent, WatchlistDetailComponent, WatchlistUpdateComponent, WatchlistDeleteDialogComponent],
  entryComponents: [WatchlistDeleteDialogComponent],
})
export class ITradeWatchlistModule {}
