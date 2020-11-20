import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISignalService } from 'app/shared/model/signal-service.model';
import { SignalServiceService } from './signal-service.service';

@Component({
  templateUrl: './signal-service-delete-dialog.component.html',
})
export class SignalServiceDeleteDialogComponent {
  signalService?: ISignalService;

  constructor(
    protected signalServiceService: SignalServiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.signalServiceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('signalServiceListModification');
      this.activeModal.close();
    });
  }
}
