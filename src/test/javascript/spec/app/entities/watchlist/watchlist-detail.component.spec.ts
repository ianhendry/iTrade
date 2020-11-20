import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { WatchlistDetailComponent } from 'app/entities/watchlist/watchlist-detail.component';
import { Watchlist } from 'app/shared/model/watchlist.model';

describe('Component Tests', () => {
  describe('Watchlist Management Detail Component', () => {
    let comp: WatchlistDetailComponent;
    let fixture: ComponentFixture<WatchlistDetailComponent>;
    const route = ({ data: of({ watchlist: new Watchlist(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [WatchlistDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WatchlistDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WatchlistDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load watchlist on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.watchlist).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
