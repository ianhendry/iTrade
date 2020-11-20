import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVideoPost } from 'app/shared/model/video-post.model';

@Component({
  selector: 'jhi-video-post-detail',
  templateUrl: './video-post-detail.component.html',
})
export class VideoPostDetailComponent implements OnInit {
  videoPost: IVideoPost | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoPost }) => (this.videoPost = videoPost));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
