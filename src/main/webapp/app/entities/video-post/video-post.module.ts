import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ITradeSharedModule } from 'app/shared/shared.module';
import { VideoPostComponent } from './video-post.component';
import { VideoPostDetailComponent } from './video-post-detail.component';
import { VideoPostUpdateComponent } from './video-post-update.component';
import { VideoPostDeleteDialogComponent } from './video-post-delete-dialog.component';
import { videoPostRoute } from './video-post.route';

@NgModule({
  imports: [ITradeSharedModule, RouterModule.forChild(videoPostRoute)],
  declarations: [VideoPostComponent, VideoPostDetailComponent, VideoPostUpdateComponent, VideoPostDeleteDialogComponent],
  entryComponents: [VideoPostDeleteDialogComponent],
})
export class ITradeVideoPostModule {}
