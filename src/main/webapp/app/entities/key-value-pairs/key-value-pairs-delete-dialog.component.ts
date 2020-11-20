import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKeyValuePairs } from 'app/shared/model/key-value-pairs.model';
import { KeyValuePairsService } from './key-value-pairs.service';

@Component({
  templateUrl: './key-value-pairs-delete-dialog.component.html',
})
export class KeyValuePairsDeleteDialogComponent {
  keyValuePairs?: IKeyValuePairs;

  constructor(
    protected keyValuePairsService: KeyValuePairsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.keyValuePairsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('keyValuePairsListModification');
      this.activeModal.close();
    });
  }
}
