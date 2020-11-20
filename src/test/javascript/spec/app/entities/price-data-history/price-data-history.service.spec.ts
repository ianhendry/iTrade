import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PriceDataHistoryService } from 'app/entities/price-data-history/price-data-history.service';
import { IPriceDataHistory, PriceDataHistory } from 'app/shared/model/price-data-history.model';
import { TIMEFRAME } from 'app/shared/model/enumerations/timeframe.model';

describe('Service Tests', () => {
  describe('PriceDataHistory Service', () => {
    let injector: TestBed;
    let service: PriceDataHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IPriceDataHistory;
    let expectedResult: IPriceDataHistory | IPriceDataHistory[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PriceDataHistoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PriceDataHistory(0, TIMEFRAME.M1, currentDate, currentDate, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            priceDate: currentDate.format(DATE_FORMAT),
            priceTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PriceDataHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            priceDate: currentDate.format(DATE_FORMAT),
            priceTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            priceDate: currentDate,
            priceTime: currentDate,
          },
          returnedFromService
        );

        service.create(new PriceDataHistory()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PriceDataHistory', () => {
        const returnedFromService = Object.assign(
          {
            priceTimeframe: 'BBBBBB',
            priceDate: currentDate.format(DATE_FORMAT),
            priceTime: currentDate.format(DATE_TIME_FORMAT),
            priceOpen: 1,
            priceHigh: 1,
            priceLow: 1,
            priceClose: 1,
            priceVolume: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            priceDate: currentDate,
            priceTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PriceDataHistory', () => {
        const returnedFromService = Object.assign(
          {
            priceTimeframe: 'BBBBBB',
            priceDate: currentDate.format(DATE_FORMAT),
            priceTime: currentDate.format(DATE_TIME_FORMAT),
            priceOpen: 1,
            priceHigh: 1,
            priceLow: 1,
            priceClose: 1,
            priceVolume: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            priceDate: currentDate,
            priceTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PriceDataHistory', () => {
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
