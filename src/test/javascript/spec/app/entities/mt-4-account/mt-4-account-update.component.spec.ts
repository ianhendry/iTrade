import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { Mt4AccountUpdateComponent } from 'app/entities/mt-4-account/mt-4-account-update.component';
import { Mt4AccountService } from 'app/entities/mt-4-account/mt-4-account.service';
import { Mt4Account } from 'app/shared/model/mt-4-account.model';

describe('Component Tests', () => {
  describe('Mt4Account Management Update Component', () => {
    let comp: Mt4AccountUpdateComponent;
    let fixture: ComponentFixture<Mt4AccountUpdateComponent>;
    let service: Mt4AccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [Mt4AccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(Mt4AccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Mt4AccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Mt4AccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mt4Account(123);
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
        const entity = new Mt4Account();
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
