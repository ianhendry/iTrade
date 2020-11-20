import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DailyAnalysisPostService } from './daily-analysis-post.service';
import { DailyAnalysisPostDeleteDialogComponent } from './daily-analysis-post-delete-dialog.component';

@Component({
  selector: 'jhi-daily-analysis-post',
  templateUrl: './daily-analysis-post.component.html',
})
export class DailyAnalysisPostComponent implements OnInit, OnDestroy {
  dailyAnalysisPosts: IDailyAnalysisPost[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected dailyAnalysisPostService: DailyAnalysisPostService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.dailyAnalysisPosts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.dailyAnalysisPostService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDailyAnalysisPost[]>) => this.paginateDailyAnalysisPosts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.dailyAnalysisPosts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDailyAnalysisPosts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDailyAnalysisPost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDailyAnalysisPosts(): void {
    this.eventSubscriber = this.eventManager.subscribe('dailyAnalysisPostListModification', () => this.reset());
  }

  delete(dailyAnalysisPost: IDailyAnalysisPost): void {
    const modalRef = this.modalService.open(DailyAnalysisPostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyAnalysisPost = dailyAnalysisPost;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDailyAnalysisPosts(data: IDailyAnalysisPost[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.dailyAnalysisPosts.push(data[i]);
      }
    }
  }
}
