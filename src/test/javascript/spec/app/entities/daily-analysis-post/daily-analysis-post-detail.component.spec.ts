import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { DailyAnalysisPostDetailComponent } from 'app/entities/daily-analysis-post/daily-analysis-post-detail.component';
import { DailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';

describe('Component Tests', () => {
  describe('DailyAnalysisPost Management Detail Component', () => {
    let comp: DailyAnalysisPostDetailComponent;
    let fixture: ComponentFixture<DailyAnalysisPostDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ dailyAnalysisPost: new DailyAnalysisPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [DailyAnalysisPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DailyAnalysisPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyAnalysisPostDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load dailyAnalysisPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dailyAnalysisPost).toEqual(jasmine.objectContaining({ id: 123 }));
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
