import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWatchlist } from 'app/shared/model/watchlist.model';
import { WatchlistService } from './watchlist.service';

@Component({
  templateUrl: './watchlist-delete-dialog.component.html',
})
export class WatchlistDeleteDialogComponent {
  watchlist?: IWatchlist;

  constructor(protected watchlistService: WatchlistService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.watchlistService.delete(id).subscribe(() => {
      this.eventManager.broadcast('watchlistListModification');
      this.activeModal.close();
    });
  }
}
