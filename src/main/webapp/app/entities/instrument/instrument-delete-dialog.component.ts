import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';

@Component({
  templateUrl: './instrument-delete-dialog.component.html',
})
export class InstrumentDeleteDialogComponent {
  instrument?: IInstrument;

  constructor(
    protected instrumentService: InstrumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.instrumentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('instrumentListModification');
      this.activeModal.close();
    });
  }
}
