import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { Mt4TradeUpdateComponent } from 'app/entities/mt-4-trade/mt-4-trade-update.component';
import { Mt4TradeService } from 'app/entities/mt-4-trade/mt-4-trade.service';
import { Mt4Trade } from 'app/shared/model/mt-4-trade.model';

describe('Component Tests', () => {
  describe('Mt4Trade Management Update Component', () => {
    let comp: Mt4TradeUpdateComponent;
    let fixture: ComponentFixture<Mt4TradeUpdateComponent>;
    let service: Mt4TradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [Mt4TradeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(Mt4TradeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Mt4TradeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Mt4TradeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mt4Trade(123);
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
        const entity = new Mt4Trade();
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
