import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriceDataHistory } from 'app/shared/model/price-data-history.model';
import { PriceDataHistoryService } from './price-data-history.service';

@Component({
  templateUrl: './price-data-history-delete-dialog.component.html',
})
export class PriceDataHistoryDeleteDialogComponent {
  priceDataHistory?: IPriceDataHistory;

  constructor(
    protected priceDataHistoryService: PriceDataHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.priceDataHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('priceDataHistoryListModification');
      this.activeModal.close();
    });
  }
}
