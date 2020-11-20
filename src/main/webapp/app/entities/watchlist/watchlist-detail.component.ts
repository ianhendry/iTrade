import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWatchlist } from 'app/shared/model/watchlist.model';

@Component({
  selector: 'jhi-watchlist-detail',
  templateUrl: './watchlist-detail.component.html',
})
export class WatchlistDetailComponent implements OnInit {
  watchlist: IWatchlist | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ watchlist }) => (this.watchlist = watchlist));
  }

  previousState(): void {
    window.history.back();
  }
}
