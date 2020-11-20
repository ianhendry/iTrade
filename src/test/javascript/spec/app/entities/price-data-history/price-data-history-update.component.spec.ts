import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { PriceDataHistoryUpdateComponent } from 'app/entities/price-data-history/price-data-history-update.component';
import { PriceDataHistoryService } from 'app/entities/price-data-history/price-data-history.service';
import { PriceDataHistory } from 'app/shared/model/price-data-history.model';

describe('Component Tests', () => {
  describe('PriceDataHistory Management Update Component', () => {
    let comp: PriceDataHistoryUpdateComponent;
    let fixture: ComponentFixture<PriceDataHistoryUpdateComponent>;
    let service: PriceDataHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [PriceDataHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PriceDataHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PriceDataHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PriceDataHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PriceDataHistory(123);
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
        const entity = new PriceDataHistory();
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
