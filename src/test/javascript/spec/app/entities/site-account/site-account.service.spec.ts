import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SiteAccountService } from 'app/entities/site-account/site-account.service';
import { ISiteAccount, SiteAccount } from 'app/shared/model/site-account.model';

describe('Service Tests', () => {
  describe('SiteAccount Service', () => {
    let injector: TestBed;
    let service: SiteAccountService;
    let httpMock: HttpTestingController;
    let elemDefault: ISiteAccount;
    let expectedResult: ISiteAccount | ISiteAccount[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SiteAccountService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SiteAccount(0, 'AAAAAAA', 'AAAAAAA', false, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SiteAccount', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
            accountCloseDate: currentDate,
          },
          returnedFromService
        );

        service.create(new SiteAccount()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SiteAccount', () => {
        const returnedFromService = Object.assign(
          {
            accountEmail: 'BBBBBB',
            accountName: 'BBBBBB',
            accountReal: true,
            accountBalance: 1,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
            accountCloseDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SiteAccount', () => {
        const returnedFromService = Object.assign(
          {
            accountEmail: 'BBBBBB',
            accountName: 'BBBBBB',
            accountReal: true,
            accountBalance: 1,
            accountOpenDate: currentDate.format(DATE_FORMAT),
            accountCloseDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            accountOpenDate: currentDate,
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

      it('should delete a SiteAccount', () => {
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
