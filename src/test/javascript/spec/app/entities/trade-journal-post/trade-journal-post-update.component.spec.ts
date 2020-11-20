import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { TradeJournalPostUpdateComponent } from 'app/entities/trade-journal-post/trade-journal-post-update.component';
import { TradeJournalPostService } from 'app/entities/trade-journal-post/trade-journal-post.service';
import { TradeJournalPost } from 'app/shared/model/trade-journal-post.model';

describe('Component Tests', () => {
  describe('TradeJournalPost Management Update Component', () => {
    let comp: TradeJournalPostUpdateComponent;
    let fixture: ComponentFixture<TradeJournalPostUpdateComponent>;
    let service: TradeJournalPostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [TradeJournalPostUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TradeJournalPostUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TradeJournalPostUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TradeJournalPostService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TradeJournalPost(123);
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
        const entity = new TradeJournalPost();
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
