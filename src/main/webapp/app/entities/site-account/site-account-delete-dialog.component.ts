import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISiteAccount } from 'app/shared/model/site-account.model';
import { SiteAccountService } from './site-account.service';

@Component({
  templateUrl: './site-account-delete-dialog.component.html',
})
export class SiteAccountDeleteDialogComponent {
  siteAccount?: ISiteAccount;

  constructor(
    protected siteAccountService: SiteAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siteAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('siteAccountListModification');
      this.activeModal.close();
    });
  }
}
