import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { SignalServiceUpdateComponent } from 'app/entities/signal-service/signal-service-update.component';
import { SignalServiceService } from 'app/entities/signal-service/signal-service.service';
import { SignalService } from 'app/shared/model/signal-service.model';

describe('Component Tests', () => {
  describe('SignalService Management Update Component', () => {
    let comp: SignalServiceUpdateComponent;
    let fixture: ComponentFixture<SignalServiceUpdateComponent>;
    let service: SignalServiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SignalServiceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SignalServiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignalServiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignalServiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignalService(123);
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
        const entity = new SignalService();
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
