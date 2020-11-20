import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { PriceDataHistoryDetailComponent } from 'app/entities/price-data-history/price-data-history-detail.component';
import { PriceDataHistory } from 'app/shared/model/price-data-history.model';

describe('Component Tests', () => {
  describe('PriceDataHistory Management Detail Component', () => {
    let comp: PriceDataHistoryDetailComponent;
    let fixture: ComponentFixture<PriceDataHistoryDetailComponent>;
    const route = ({ data: of({ priceDataHistory: new PriceDataHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [PriceDataHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PriceDataHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PriceDataHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load priceDataHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priceDataHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
