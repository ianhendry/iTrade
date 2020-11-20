import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ITradeTestModule } from '../../../test.module';
import { KeyValuePairsDetailComponent } from 'app/entities/key-value-pairs/key-value-pairs-detail.component';
import { KeyValuePairs } from 'app/shared/model/key-value-pairs.model';

describe('Component Tests', () => {
  describe('KeyValuePairs Management Detail Component', () => {
    let comp: KeyValuePairsDetailComponent;
    let fixture: ComponentFixture<KeyValuePairsDetailComponent>;
    const route = ({ data: of({ keyValuePairs: new KeyValuePairs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ITradeTestModule],
        declarations: [KeyValuePairsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(KeyValuePairsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KeyValuePairsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load keyValuePairs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.keyValuePairs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
