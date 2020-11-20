import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IShippingDetails } from 'app/shared/model/shipping-details.model';

@Component({
  selector: 'jhi-shipping-details-detail',
  templateUrl: './shipping-details-detail.component.html',
})
export class ShippingDetailsDetailComponent implements OnInit {
  shippingDetails: IShippingDetails | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippingDetails }) => (this.shippingDetails = shippingDetails));
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
