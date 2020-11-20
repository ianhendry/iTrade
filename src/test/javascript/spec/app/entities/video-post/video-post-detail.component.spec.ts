import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { VideoPostDetailComponent } from 'app/entities/video-post/video-post-detail.component';
import { VideoPost } from 'app/shared/model/video-post.model';

describe('Component Tests', () => {
  describe('VideoPost Management Detail Component', () => {
    let comp: VideoPostDetailComponent;
    let fixture: ComponentFixture<VideoPostDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ videoPost: new VideoPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [VideoPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VideoPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VideoPostDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load videoPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.videoPost).toEqual(jasmine.objectContaining({ id: 123 }));
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
