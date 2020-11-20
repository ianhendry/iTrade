import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { InstrumentDetailComponent } from 'app/entities/instrument/instrument-detail.component';
import { Instrument } from 'app/shared/model/instrument.model';

describe('Component Tests', () => {
  describe('Instrument Management Detail Component', () => {
    let comp: InstrumentDetailComponent;
    let fixture: ComponentFixture<InstrumentDetailComponent>;
    const route = ({ data: of({ instrument: new Instrument(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [InstrumentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InstrumentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstrumentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load instrument on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.instrument).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
