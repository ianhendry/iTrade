import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeSignals } from 'app/shared/model/trade-signals.model';

@Component({
  selector: 'jhi-trade-signals-detail',
  templateUrl: './trade-signals-detail.component.html',
})
export class TradeSignalsDetailComponent implements OnInit {
  tradeSignals: ITradeSignals | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradeSignals }) => (this.tradeSignals = tradeSignals));
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
