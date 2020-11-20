import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKeyValuePairs } from 'app/shared/model/key-value-pairs.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { KeyValuePairsService } from './key-value-pairs.service';
import { KeyValuePairsDeleteDialogComponent } from './key-value-pairs-delete-dialog.component';

@Component({
  selector: 'jhi-key-value-pairs',
  templateUrl: './key-value-pairs.component.html',
})
export class KeyValuePairsComponent implements OnInit, OnDestroy {
  keyValuePairs: IKeyValuePairs[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected keyValuePairsService: KeyValuePairsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.keyValuePairs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.keyValuePairsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IKeyValuePairs[]>) => this.paginateKeyValuePairs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.keyValuePairs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKeyValuePairs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKeyValuePairs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKeyValuePairs(): void {
    this.eventSubscriber = this.eventManager.subscribe('keyValuePairsListModification', () => this.reset());
  }

  delete(keyValuePairs: IKeyValuePairs): void {
    const modalRef = this.modalService.open(KeyValuePairsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.keyValuePairs = keyValuePairs;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateKeyValuePairs(data: IKeyValuePairs[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.keyValuePairs.push(data[i]);
      }
    }
  }
}
