import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWatchlist } from 'app/shared/model/watchlist.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { WatchlistService } from './watchlist.service';
import { WatchlistDeleteDialogComponent } from './watchlist-delete-dialog.component';

@Component({
  selector: 'jhi-watchlist',
  templateUrl: './watchlist.component.html',
})
export class WatchlistComponent implements OnInit, OnDestroy {
  watchlists: IWatchlist[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected watchlistService: WatchlistService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.watchlists = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.watchlistService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IWatchlist[]>) => this.paginateWatchlists(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.watchlists = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWatchlists();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWatchlist): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWatchlists(): void {
    this.eventSubscriber = this.eventManager.subscribe('watchlistListModification', () => this.reset());
  }

  delete(watchlist: IWatchlist): void {
    const modalRef = this.modalService.open(WatchlistDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.watchlist = watchlist;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateWatchlists(data: IWatchlist[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.watchlists.push(data[i]);
      }
    }
  }
}
