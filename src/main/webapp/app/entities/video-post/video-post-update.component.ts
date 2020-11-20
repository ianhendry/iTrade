import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IVideoPost, VideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-video-post-update',
  templateUrl: './video-post-update.component.html',
})
export class VideoPostUpdateComponent implements OnInit {
  isSaving = false;
  dateApprovedDp: any;

  editForm = this.fb.group({
    id: [],
    postTitle: [null, [Validators.required]],
    postBody: [],
    dateAdded: [null, [Validators.required]],
    dateApproved: [],
    media: [],
    mediaContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected videoPostService: VideoPostService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoPost }) => {
      if (!videoPost.id) {
        const today = moment().startOf('day');
        videoPost.dateAdded = today;
      }

      this.updateForm(videoPost);
    });
  }

  updateForm(videoPost: IVideoPost): void {
    this.editForm.patchValue({
      id: videoPost.id,
      postTitle: videoPost.postTitle,
      postBody: videoPost.postBody,
      dateAdded: videoPost.dateAdded ? videoPost.dateAdded.format(DATE_TIME_FORMAT) : null,
      dateApproved: videoPost.dateApproved,
      media: videoPost.media,
      mediaContentType: videoPost.mediaContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('iTradeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const videoPost = this.createFromForm();
    if (videoPost.id !== undefined) {
      this.subscribeToSaveResponse(this.videoPostService.update(videoPost));
    } else {
      this.subscribeToSaveResponse(this.videoPostService.create(videoPost));
    }
  }

  private createFromForm(): IVideoPost {
    return {
      ...new VideoPost(),
      id: this.editForm.get(['id'])!.value,
      postTitle: this.editForm.get(['postTitle'])!.value,
      postBody: this.editForm.get(['postBody'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value ? moment(this.editForm.get(['dateAdded'])!.value, DATE_TIME_FORMAT) : undefined,
      dateApproved: this.editForm.get(['dateApproved'])!.value,
      mediaContentType: this.editForm.get(['mediaContentType'])!.value,
      media: this.editForm.get(['media'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoPost>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
