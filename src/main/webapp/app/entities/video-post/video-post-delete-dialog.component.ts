import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';

@Component({
  templateUrl: './video-post-delete-dialog.component.html',
})
export class VideoPostDeleteDialogComponent {
  videoPost?: IVideoPost;

  constructor(protected videoPostService: VideoPostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.videoPostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('videoPostListModification');
      this.activeModal.close();
    });
  }
}
