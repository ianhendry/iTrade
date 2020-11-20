import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITradeJournalPost } from 'app/shared/model/trade-journal-post.model';
import { TradeJournalPostService } from './trade-journal-post.service';

@Component({
  templateUrl: './trade-journal-post-delete-dialog.component.html',
})
export class TradeJournalPostDeleteDialogComponent {
  tradeJournalPost?: ITradeJournalPost;

  constructor(
    protected tradeJournalPostService: TradeJournalPostService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tradeJournalPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tradeJournalPostListModification');
      this.activeModal.close();
    });
  }
}
