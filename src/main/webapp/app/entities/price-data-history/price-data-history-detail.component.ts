import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriceDataHistory } from 'app/shared/model/price-data-history.model';

@Component({
  selector: 'jhi-price-data-history-detail',
  templateUrl: './price-data-history-detail.component.html',
})
export class PriceDataHistoryDetailComponent implements OnInit {
  priceDataHistory: IPriceDataHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priceDataHistory }) => (this.priceDataHistory = priceDataHistory));
  }

  previousState(): void {
    window.history.back();
  }
}
