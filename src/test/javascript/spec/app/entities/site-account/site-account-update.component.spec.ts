import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { SiteAccountUpdateComponent } from 'app/entities/site-account/site-account-update.component';
import { SiteAccountService } from 'app/entities/site-account/site-account.service';
import { SiteAccount } from 'app/shared/model/site-account.model';

describe('Component Tests', () => {
  describe('SiteAccount Management Update Component', () => {
    let comp: SiteAccountUpdateComponent;
    let fixture: ComponentFixture<SiteAccountUpdateComponent>;
    let service: SiteAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [SiteAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SiteAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SiteAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SiteAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SiteAccount(123);
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
        const entity = new SiteAccount();
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
