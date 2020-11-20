import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TradeSignalsService } from 'app/entities/trade-signals/trade-signals.service';
import { ITradeSignals, TradeSignals } from 'app/shared/model/trade-signals.model';
import { SIGNALINDICATES } from 'app/shared/model/enumerations/signalindicates.model';

describe('Service Tests', () => {
  describe('TradeSignals Service', () => {
    let injector: TestBed;
    let service: TradeSignalsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITradeSignals;
    let expectedResult: ITradeSignals | ITradeSignals[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TradeSignalsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TradeSignals(0, 'AAAAAAA', 'AAAAAAA', 0, SIGNALINDICATES.STRENGTH, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TradeSignals', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TradeSignals()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TradeSignals', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            note: 'BBBBBB',
            numberOfBars: 1,
            signalIndicates: 'BBBBBB',
            description: 'BBBBBB',
            background: 'BBBBBB',
            actionToTake: 'BBBBBB',
            sequenceNumber: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TradeSignals', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            note: 'BBBBBB',
            numberOfBars: 1,
            signalIndicates: 'BBBBBB',
            description: 'BBBBBB',
            background: 'BBBBBB',
            actionToTake: 'BBBBBB',
            sequenceNumber: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TradeSignals', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
