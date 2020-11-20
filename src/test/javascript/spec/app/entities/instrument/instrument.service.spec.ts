import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InstrumentService } from 'app/entities/instrument/instrument.service';
import { IInstrument, Instrument } from 'app/shared/model/instrument.model';
import { DATAPROVIDER } from 'app/shared/model/enumerations/dataprovider.model';

describe('Service Tests', () => {
  describe('Instrument Service', () => {
    let injector: TestBed;
    let service: InstrumentService;
    let httpMock: HttpTestingController;
    let elemDefault: IInstrument;
    let expectedResult: IInstrument | IInstrument[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InstrumentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Instrument(0, DATAPROVIDER.FXPRO, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, false, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataFrom: currentDate.format(DATE_FORMAT),
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Instrument', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataFrom: currentDate.format(DATE_FORMAT),
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataFrom: currentDate,
            dateAdded: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.create(new Instrument()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Instrument', () => {
        const returnedFromService = Object.assign(
          {
            dataProvider: 'BBBBBB',
            ticker: 'BBBBBB',
            exchange: 'BBBBBB',
            description: 'BBBBBB',
            dataFrom: currentDate.format(DATE_FORMAT),
            isActive: true,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataFrom: currentDate,
            dateAdded: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Instrument', () => {
        const returnedFromService = Object.assign(
          {
            dataProvider: 'BBBBBB',
            ticker: 'BBBBBB',
            exchange: 'BBBBBB',
            description: 'BBBBBB',
            dataFrom: currentDate.format(DATE_FORMAT),
            isActive: true,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataFrom: currentDate,
            dateAdded: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Instrument', () => {
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
