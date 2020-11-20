import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';

@Component({
  selector: 'jhi-daily-analysis-post-detail',
  templateUrl: './daily-analysis-post-detail.component.html',
})
export class DailyAnalysisPostDetailComponent implements OnInit {
  dailyAnalysisPost: IDailyAnalysisPost | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyAnalysisPost }) => (this.dailyAnalysisPost = dailyAnalysisPost));
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
