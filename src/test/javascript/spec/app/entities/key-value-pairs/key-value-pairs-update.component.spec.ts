import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { KeyValuePairsUpdateComponent } from 'app/entities/key-value-pairs/key-value-pairs-update.component';
import { KeyValuePairsService } from 'app/entities/key-value-pairs/key-value-pairs.service';
import { KeyValuePairs } from 'app/shared/model/key-value-pairs.model';

describe('Component Tests', () => {
  describe('KeyValuePairs Management Update Component', () => {
    let comp: KeyValuePairsUpdateComponent;
    let fixture: ComponentFixture<KeyValuePairsUpdateComponent>;
    let service: KeyValuePairsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [KeyValuePairsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KeyValuePairsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KeyValuePairsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KeyValuePairsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KeyValuePairs(123);
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
        const entity = new KeyValuePairs();
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
