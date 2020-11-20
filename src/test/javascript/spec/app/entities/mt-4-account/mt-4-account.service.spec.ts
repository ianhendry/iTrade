import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { Mt4AccountService } from 'app/entities/mt-4-account/mt-4-account.service';
import { IMt4Account, Mt4Account } from 'app/shared/model/mt-4-account.model';
import { ACCOUNTTYPE } from 'app/shared/model/enumerations/accounttype.model';
import { BROKER } from 'app/shared/model/enumerations/broker.model';

describe('Service Tests', () => {
  describe('Mt4Account Service', () => {
    let injector: TestBed;
    let service: Mt4AccountService;
    let httpMock: HttpTestingController;
    let elemDefault: IMt4Account;
    let expectedResult: IMt4Account | IMt4Account[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(Mt4AccountService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Mt4Account(0, ACCOUNTTYPE.REAL, BROKER.FXPRO, 'AAAAAAA', 'AAAAAAA', false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Mt4Account', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountCloseDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Mt4Account()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mt4Account', () => {
        const returnedFromService = Object.assign(
          {
            accountType: 'BBBBBB',
            accountBroker: 'BBBBBB',
            accountLogin: 'BBBBBB',
            accountPassword: 'BBBBBB',
            accountActive: true,
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountCloseDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mt4Account', () => {
        const returnedFromService = Object.assign(
          {
            accountType: 'BBBBBB',
            accountBroker: 'BBBBBB',
            accountLogin: 'BBBBBB',
            accountPassword: 'BBBBBB',
            accountActive: true,
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountCloseDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Mt4Account', () => {
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
