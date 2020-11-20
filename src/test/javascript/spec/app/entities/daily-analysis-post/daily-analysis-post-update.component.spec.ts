import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { DailyAnalysisPostUpdateComponent } from 'app/entities/daily-analysis-post/daily-analysis-post-update.component';
import { DailyAnalysisPostService } from 'app/entities/daily-analysis-post/daily-analysis-post.service';
import { DailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';

describe('Component Tests', () => {
  describe('DailyAnalysisPost Management Update Component', () => {
    let comp: DailyAnalysisPostUpdateComponent;
    let fixture: ComponentFixture<DailyAnalysisPostUpdateComponent>;
    let service: DailyAnalysisPostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [DailyAnalysisPostUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DailyAnalysisPostUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyAnalysisPostUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyAnalysisPostService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyAnalysisPost(123);
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
        const entity = new DailyAnalysisPost();
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
