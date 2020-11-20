import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ITradeTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { DailyAnalysisPostDeleteDialogComponent } from 'app/entities/daily-analysis-post/daily-analysis-post-delete-dialog.component';
import { DailyAnalysisPostService } from 'app/entities/daily-analysis-post/daily-analysis-post.service';

describe('Component Tests', () => {
  describe('DailyAnalysisPost Management Delete Component', () => {
    let comp: DailyAnalysisPostDeleteDialogComponent;
    let fixture: ComponentFixture<DailyAnalysisPostDeleteDialogComponent>;
    let service: DailyAnalysisPostService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [DailyAnalysisPostDeleteDialogComponent],
      })
        .overrideTemplate(DailyAnalysisPostDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyAnalysisPostDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyAnalysisPostService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
