import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { InstrumentUpdateComponent } from 'app/entities/instrument/instrument-update.component';
import { InstrumentService } from 'app/entities/instrument/instrument.service';
import { Instrument } from 'app/shared/model/instrument.model';

describe('Component Tests', () => {
  describe('Instrument Management Update Component', () => {
    let comp: InstrumentUpdateComponent;
    let fixture: ComponentFixture<InstrumentUpdateComponent>;
    let service: InstrumentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [InstrumentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InstrumentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstrumentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstrumentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Instrument(123);
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
        const entity = new Instrument();
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
