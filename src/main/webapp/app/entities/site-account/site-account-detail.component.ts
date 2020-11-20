import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiteAccount } from 'app/shared/model/site-account.model';

@Component({
  selector: 'jhi-site-account-detail',
  templateUrl: './site-account-detail.component.html',
})
export class SiteAccountDetailComponent implements OnInit {
  siteAccount: ISiteAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siteAccount }) => (this.siteAccount = siteAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
