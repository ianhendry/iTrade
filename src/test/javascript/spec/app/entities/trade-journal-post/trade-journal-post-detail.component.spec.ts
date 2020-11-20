import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { TradeJournalPostDetailComponent } from 'app/entities/trade-journal-post/trade-journal-post-detail.component';
import { TradeJournalPost } from 'app/shared/model/trade-journal-post.model';

describe('Component Tests', () => {
  describe('TradeJournalPost Management Detail Component', () => {
    let comp: TradeJournalPostDetailComponent;
    let fixture: ComponentFixture<TradeJournalPostDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tradeJournalPost: new TradeJournalPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [TradeJournalPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TradeJournalPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TradeJournalPostDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tradeJournalPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tradeJournalPost).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
