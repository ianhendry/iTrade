import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { ShippingDetailsDetailComponent } from 'app/entities/shipping-details/shipping-details-detail.component';
import { ShippingDetails } from 'app/shared/model/shipping-details.model';

describe('Component Tests', () => {
  describe('ShippingDetails Management Detail Component', () => {
    let comp: ShippingDetailsDetailComponent;
    let fixture: ComponentFixture<ShippingDetailsDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ shippingDetails: new ShippingDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [ShippingDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShippingDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShippingDetailsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load shippingDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shippingDetails).toEqual(jasmine.objectContaining({ id: 123 }));
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
