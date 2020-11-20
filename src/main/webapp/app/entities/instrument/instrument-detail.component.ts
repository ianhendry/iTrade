import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstrument } from 'app/shared/model/instrument.model';

@Component({
  selector: 'jhi-instrument-detail',
  templateUrl: './instrument-detail.component.html',
})
export class InstrumentDetailComponent implements OnInit {
  instrument: IInstrument | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instrument }) => (this.instrument = instrument));
  }

  previousState(): void {
    window.history.back();
  }
}
