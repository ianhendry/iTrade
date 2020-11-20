import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SignalServiceService } from 'app/entities/signal-service/signal-service.service';
import { ISignalService, SignalService } from 'app/shared/model/signal-service.model';
import { SIGNALINDICATES } from 'app/shared/model/enumerations/signalindicates.model';
import { TIMEFRAME } from 'app/shared/model/enumerations/timeframe.model';
import { SIGNALBARSIZE } from 'app/shared/model/enumerations/signalbarsize.model';
import { BARCLOSE } from 'app/shared/model/enumerations/barclose.model';

describe('Service Tests', () => {
  describe('SignalService Service', () => {
    let injector: TestBed;
    let service: SignalServiceService;
    let httpMock: HttpTestingController;
    let elemDefault: ISignalService;
    let expectedResult: ISignalService | ISignalService[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SignalServiceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SignalService(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        SIGNALINDICATES.STRENGTH,
        'image/png',
        'AAAAAAA',
        TIMEFRAME.M1,
        0,
        false,
        0,
        SIGNALBARSIZE.SMALL,
        BARCLOSE.MIDDLE,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            alertDate: currentDate.format(DATE_FORMAT),
            alertTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SignalService', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            alertDate: currentDate.format(DATE_FORMAT),
            alertTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            alertDate: currentDate,
            alertTime: currentDate,
          },
          returnedFromService
        );

        service.create(new SignalService()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SignalService', () => {
        const returnedFromService = Object.assign(
          {
            alertDate: currentDate.format(DATE_FORMAT),
            alertTime: currentDate.format(DATE_TIME_FORMAT),
            alertText: 'BBBBBB',
            alertDescription: 'BBBBBB',
            signalIndicates: 'BBBBBB',
            image: 'BBBBBB',
            timeframe: 'BBBBBB',
            alertPrice: 1,
            isSequencePresent: true,
            barVolume: 1,
            barSize: 'BBBBBB',
            barClose: 'BBBBBB',
            isPublished: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            alertDate: currentDate,
            alertTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SignalService', () => {
        const returnedFromService = Object.assign(
          {
            alertDate: currentDate.format(DATE_FORMAT),
            alertTime: currentDate.format(DATE_TIME_FORMAT),
            alertText: 'BBBBBB',
            alertDescription: 'BBBBBB',
            signalIndicates: 'BBBBBB',
            image: 'BBBBBB',
            timeframe: 'BBBBBB',
            alertPrice: 1,
            isSequencePresent: true,
            barVolume: 1,
            barSize: 'BBBBBB',
            barClose: 'BBBBBB',
            isPublished: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            alertDate: currentDate,
            alertTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SignalService', () => {
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
