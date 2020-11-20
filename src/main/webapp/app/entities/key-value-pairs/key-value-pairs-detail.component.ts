import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKeyValuePairs } from 'app/shared/model/key-value-pairs.model';

@Component({
  selector: 'jhi-key-value-pairs-detail',
  templateUrl: './key-value-pairs-detail.component.html',
})
export class KeyValuePairsDetailComponent implements OnInit {
  keyValuePairs: IKeyValuePairs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ keyValuePairs }) => (this.keyValuePairs = keyValuePairs));
  }

  previousState(): void {
    window.history.back();
  }
}
