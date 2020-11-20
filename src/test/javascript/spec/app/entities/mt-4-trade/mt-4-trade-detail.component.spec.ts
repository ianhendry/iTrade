import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { Mt4TradeDetailComponent } from 'app/entities/mt-4-trade/mt-4-trade-detail.component';
import { Mt4Trade } from 'app/shared/model/mt-4-trade.model';

describe('Component Tests', () => {
  describe('Mt4Trade Management Detail Component', () => {
    let comp: Mt4TradeDetailComponent;
    let fixture: ComponentFixture<Mt4TradeDetailComponent>;
    const route = ({ data: of({ mt4Trade: new Mt4Trade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [Mt4TradeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(Mt4TradeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Mt4TradeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mt4Trade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mt4Trade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
