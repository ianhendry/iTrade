import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Mt4TradeService } from 'app/entities/mt-4-trade/mt-4-trade.service';
import { IMt4Trade, Mt4Trade } from 'app/shared/model/mt-4-trade.model';

describe('Service Tests', () => {
  describe('Mt4Trade Service', () => {
    let injector: TestBed;
    let service: Mt4TradeService;
    let httpMock: HttpTestingController;
    let elemDefault: IMt4Trade;
    let expectedResult: IMt4Trade | IMt4Trade[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(Mt4TradeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Mt4Trade(0, 0, currentDate, 'AAAAAAA', 0, 0, 0, 0, currentDate, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            openTime: currentDate.format(DATE_TIME_FORMAT),
            closeTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Mt4Trade', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            openTime: currentDate.format(DATE_TIME_FORMAT),
            closeTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openTime: currentDate,
            closeTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Mt4Trade()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mt4Trade', () => {
        const returnedFromService = Object.assign(
          {
            ticket: 1,
            openTime: currentDate.format(DATE_TIME_FORMAT),
            directionType: 'BBBBBB',
            positionSize: 1,
            openPrice: 1,
            stopLossPrice: 1,
            takeProfitPrice: 1,
            closeTime: currentDate.format(DATE_TIME_FORMAT),
            closePrice: 1,
            commission: 1,
            taxes: 1,
            swap: 1,
            profit: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openTime: currentDate,
            closeTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mt4Trade', () => {
        const returnedFromService = Object.assign(
          {
            ticket: 1,
            openTime: currentDate.format(DATE_TIME_FORMAT),
            directionType: 'BBBBBB',
            positionSize: 1,
            openPrice: 1,
            stopLossPrice: 1,
            takeProfitPrice: 1,
            closeTime: currentDate.format(DATE_TIME_FORMAT),
            closePrice: 1,
            commission: 1,
            taxes: 1,
            swap: 1,
            profit: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            openTime: currentDate,
            closeTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mt4Trade', () => {
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
