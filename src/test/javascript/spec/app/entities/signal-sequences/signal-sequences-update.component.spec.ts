import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { SignalSequencesUpdateComponent } from 'app/entities/signal-sequences/signal-sequences-update.component';
import { SignalSequencesService } from 'app/entities/signal-sequences/signal-sequences.service';
import { SignalSequences } from 'app/shared/model/signal-sequences.model';

describe('Component Tests', () => {
  describe('SignalSequences Management Update Component', () => {
    let comp: SignalSequencesUpdateComponent;
    let fixture: ComponentFixture<SignalSequencesUpdateComponent>;
    let service: SignalSequencesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SignalSequencesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SignalSequencesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignalSequencesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignalSequencesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignalSequences(123);
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
        const entity = new SignalSequences();
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
