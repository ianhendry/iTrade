import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITradeSignals } from 'app/shared/model/trade-signals.model';
import { TradeSignalsService } from './trade-signals.service';

@Component({
  templateUrl: './trade-signals-delete-dialog.component.html',
})
export class TradeSignalsDeleteDialogComponent {
  tradeSignals?: ITradeSignals;

  constructor(
    protected tradeSignalsService: TradeSignalsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tradeSignalsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tradeSignalsListModification');
      this.activeModal.close();
    });
  }
}
