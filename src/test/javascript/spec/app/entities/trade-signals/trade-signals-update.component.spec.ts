import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { TradeSignalsUpdateComponent } from 'app/entities/trade-signals/trade-signals-update.component';
import { TradeSignalsService } from 'app/entities/trade-signals/trade-signals.service';
import { TradeSignals } from 'app/shared/model/trade-signals.model';

describe('Component Tests', () => {
  describe('TradeSignals Management Update Component', () => {
    let comp: TradeSignalsUpdateComponent;
    let fixture: ComponentFixture<TradeSignalsUpdateComponent>;
    let service: TradeSignalsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [TradeSignalsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TradeSignalsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradeSignalsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradeSignalsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradeSignals(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradeSignals();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
