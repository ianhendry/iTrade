import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { SignalServiceDetailComponent } from 'app/entities/signal-service/signal-service-detail.component';
import { SignalService } from 'app/shared/model/signal-service.model';

describe('Component Tests', () => {
  describe('SignalService Management Detail Component', () => {
    let comp: SignalServiceDetailComponent;
    let fixture: ComponentFixture<SignalServiceDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ signalService: new SignalService(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SignalServiceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SignalServiceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SignalServiceDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load signalService on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.signalService).toEqual(jasmine.objectContaining({ id: 123 }));
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
