import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { Mt4AccountDetailComponent } from 'app/entities/mt-4-account/mt-4-account-detail.component';
import { Mt4Account } from 'app/shared/model/mt-4-account.model';

describe('Component Tests', () => {
  describe('Mt4Account Management Detail Component', () => {
    let comp: Mt4AccountDetailComponent;
    let fixture: ComponentFixture<Mt4AccountDetailComponent>;
    const route = ({ data: of({ mt4Account: new Mt4Account(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [Mt4AccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(Mt4AccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Mt4AccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mt4Account on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mt4Account).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
