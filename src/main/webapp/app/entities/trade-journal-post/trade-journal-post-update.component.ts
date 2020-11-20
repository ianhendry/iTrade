import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITradeJournalPost, TradeJournalPost } from 'app/shared/model/trade-journal-post.model';
import { TradeJournalPostService } from './trade-journal-post.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment/comment.service';
import { IVideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from 'app/entities/video-post/video-post.service';

type SelectableEntity = IComment | IVideoPost;

@Component({
  selector: 'jhi-trade-journal-post-update',
  templateUrl: './trade-journal-post-update.component.html',
})
export class TradeJournalPostUpdateComponent implements OnInit {
  isSaving = false;
  comments: IComment[] = [];
  videoposts: IVideoPost[] = [];
  dateAddedDp: any;
  dateApprovedDp: any;

  editForm = this.fb.group({
    id: [],
    postTitle: [null, [Validators.required]],
    postBody: [],
    dateAdded: [null, [Validators.required]],
    dateApproved: [],
    media1: [],
    media1ContentType: [],
    media2: [],
    media2ContentType: [],
    media3: [],
    media3ContentType: [],
    media4: [],
    media4ContentType: [],
    comment: [],
    videoPost: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected tradeJournalPostService: TradeJournalPostService,
    protected commentService: CommentService,
    protected videoPostService: VideoPostService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradeJournalPost }) => {
      this.updateForm(tradeJournalPost);

      this.commentService.query().subscribe((res: HttpResponse<IComment[]>) => (this.comments = res.body || []));

      this.videoPostService.query().subscribe((res: HttpResponse<IVideoPost[]>) => (this.videoposts = res.body || []));
    });
  }

  updateForm(tradeJournalPost: ITradeJournalPost): void {
    this.editForm.patchValue({
      id: tradeJournalPost.id,
      postTitle: tradeJournalPost.postTitle,
      postBody: tradeJournalPost.postBody,
      dateAdded: tradeJournalPost.dateAdded,
      dateApproved: tradeJournalPost.dateApproved,
      media1: tradeJournalPost.media1,
      media1ContentType: tradeJournalPost.media1ContentType,
      media2: tradeJournalPost.media2,
      media2ContentType: tradeJournalPost.media2ContentType,
      media3: tradeJournalPost.media3,
      media3ContentType: tradeJournalPost.media3ContentType,
      media4: tradeJournalPost.media4,
      media4ContentType: tradeJournalPost.media4ContentType,
      comment: tradeJournalPost.comment,
      videoPost: tradeJournalPost.videoPost,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tradeJournalPost = this.createFromForm();
    if (tradeJournalPost.id !== undefined) {
      this.subscribeToSaveResponse(this.tradeJournalPostService.update(tradeJournalPost));
    } else {
      this.subscribeToSaveResponse(this.tradeJournalPostService.create(tradeJournalPost));
    }
  }

  private createFromForm(): ITradeJournalPost {
    return {
      ...new TradeJournalPost(),
      id: this.editForm.get(['id'])!.value,
      postTitle: this.editForm.get(['postTitle'])!.value,
      postBody: this.editForm.get(['postBody'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value,
      dateApproved: this.editForm.get(['dateApproved'])!.value,
      media1ContentType: this.editForm.get(['media1ContentType'])!.value,
      media1: this.editForm.get(['media1'])!.value,
      media2ContentType: this.editForm.get(['media2ContentType'])!.value,
      media2: this.editForm.get(['media2'])!.value,
      media3ContentType: this.editForm.get(['media3ContentType'])!.value,
      media3: this.editForm.get(['media3'])!.value,
      media4ContentType: this.editForm.get(['media4ContentType'])!.value,
      media4: this.editForm.get(['media4'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      videoPost: this.editForm.get(['videoPost'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradeJournalPost>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
