import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVideoPost } from 'app/shared/model/video-post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VideoPostService } from './video-post.service';
import { VideoPostDeleteDialogComponent } from './video-post-delete-dialog.component';

@Component({
  selector: 'jhi-video-post',
  templateUrl: './video-post.component.html',
})
export class VideoPostComponent implements OnInit, OnDestroy {
  videoPosts: IVideoPost[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected videoPostService: VideoPostService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.videoPosts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.videoPostService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IVideoPost[]>) => this.paginateVideoPosts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.videoPosts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVideoPosts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVideoPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInVideoPosts(): void {
    this.eventSubscriber = this.eventManager.subscribe('videoPostListModification', () => this.reset());
  }

  delete(videoPost: IVideoPost): void {
    const modalRef = this.modalService.open(VideoPostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.videoPost = videoPost;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVideoPosts(data: IVideoPost[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.videoPosts.push(data[i]);
      }
    }
  }
}
