import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMt4Account } from 'app/shared/model/mt-4-account.model';
import { Mt4AccountService } from './mt-4-account.service';

@Component({
  templateUrl: './mt-4-account-delete-dialog.component.html',
})
export class Mt4AccountDeleteDialogComponent {
  mt4Account?: IMt4Account;

  constructor(
    protected mt4AccountService: Mt4AccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mt4AccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mt4AccountListModification');
      this.activeModal.close();
    });
  }
}
