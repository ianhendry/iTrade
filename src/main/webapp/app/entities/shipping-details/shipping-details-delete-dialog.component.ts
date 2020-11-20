import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShippingDetails } from 'app/shared/model/shipping-details.model';
import { ShippingDetailsService } from './shipping-details.service';

@Component({
  templateUrl: './shipping-details-delete-dialog.component.html',
})
export class ShippingDetailsDeleteDialogComponent {
  shippingDetails?: IShippingDetails;

  constructor(
    protected shippingDetailsService: ShippingDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shippingDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shippingDetailsListModification');
      this.activeModal.close();
    });
  }
}
