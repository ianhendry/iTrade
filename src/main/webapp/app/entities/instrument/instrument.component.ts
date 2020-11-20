import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInstrument } from 'app/shared/model/instrument.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InstrumentService } from './instrument.service';
import { InstrumentDeleteDialogComponent } from './instrument-delete-dialog.component';

@Component({
  selector: 'jhi-instrument',
  templateUrl: './instrument.component.html',
})
export class InstrumentComponent implements OnInit, OnDestroy {
  instruments: IInstrument[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected instrumentService: InstrumentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.instruments = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.instrumentService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IInstrument[]>) => this.paginateInstruments(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.instruments = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInstruments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInstrument): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInstruments(): void {
    this.eventSubscriber = this.eventManager.subscribe('instrumentListModification', () => this.reset());
  }

  delete(instrument: IInstrument): void {
    const modalRef = this.modalService.open(InstrumentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.instrument = instrument;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInstruments(data: IInstrument[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.instruments.push(data[i]);
      }
    }
  }
}
