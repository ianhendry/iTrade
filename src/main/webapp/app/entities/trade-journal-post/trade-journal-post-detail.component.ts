import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeJournalPost } from 'app/shared/model/trade-journal-post.model';

@Component({
  selector: 'jhi-trade-journal-post-detail',
  templateUrl: './trade-journal-post-detail.component.html',
})
export class TradeJournalPostDetailComponent implements OnInit {
  tradeJournalPost: ITradeJournalPost | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradeJournalPost }) => (this.tradeJournalPost = tradeJournalPost));
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
