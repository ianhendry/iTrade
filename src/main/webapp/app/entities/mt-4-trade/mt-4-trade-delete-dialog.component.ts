import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMt4Trade } from 'app/shared/model/mt-4-trade.model';
import { Mt4TradeService } from './mt-4-trade.service';

@Component({
  templateUrl: './mt-4-trade-delete-dialog.component.html',
})
export class Mt4TradeDeleteDialogComponent {
  mt4Trade?: IMt4Trade;

  constructor(protected mt4TradeService: Mt4TradeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mt4TradeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mt4TradeListModification');
      this.activeModal.close();
    });
  }
}
