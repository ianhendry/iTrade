import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISignalSequences } from 'app/shared/model/signal-sequences.model';
import { SignalSequencesService } from './signal-sequences.service';

@Component({
  templateUrl: './signal-sequences-delete-dialog.component.html',
})
export class SignalSequencesDeleteDialogComponent {
  signalSequences?: ISignalSequences;

  constructor(
    protected signalSequencesService: SignalSequencesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.signalSequencesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('signalSequencesListModification');
      this.activeModal.close();
    });
  }
}
