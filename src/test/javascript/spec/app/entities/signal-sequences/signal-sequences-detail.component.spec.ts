import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { SignalSequencesDetailComponent } from 'app/entities/signal-sequences/signal-sequences-detail.component';
import { SignalSequences } from 'app/shared/model/signal-sequences.model';

describe('Component Tests', () => {
  describe('SignalSequences Management Detail Component', () => {
    let comp: SignalSequencesDetailComponent;
    let fixture: ComponentFixture<SignalSequencesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ signalSequences: new SignalSequences(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SignalSequencesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SignalSequencesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SignalSequencesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load signalSequences on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.signalSequences).toEqual(jasmine.objectContaining({ id: 123 }));
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
