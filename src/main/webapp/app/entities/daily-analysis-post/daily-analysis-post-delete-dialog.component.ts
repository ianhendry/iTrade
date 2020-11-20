import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';
import { DailyAnalysisPostService } from './daily-analysis-post.service';

@Component({
  templateUrl: './daily-analysis-post-delete-dialog.component.html',
})
export class DailyAnalysisPostDeleteDialogComponent {
  dailyAnalysisPost?: IDailyAnalysisPost;

  constructor(
    protected dailyAnalysisPostService: DailyAnalysisPostService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dailyAnalysisPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dailyAnalysisPostListModification');
      this.activeModal.close();
    });
  }
}
