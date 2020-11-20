import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { SiteAccountDetailComponent } from 'app/entities/site-account/site-account-detail.component';
import { SiteAccount } from 'app/shared/model/site-account.model';

describe('Component Tests', () => {
  describe('SiteAccount Management Detail Component', () => {
    let comp: SiteAccountDetailComponent;
    let fixture: ComponentFixture<SiteAccountDetailComponent>;
    const route = ({ data: of({ siteAccount: new SiteAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SiteAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SiteAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SiteAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load siteAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.siteAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
