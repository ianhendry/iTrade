import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ShippingDetailsService } from 'app/entities/shipping-details/shipping-details.service';
import { IShippingDetails, ShippingDetails } from 'app/shared/model/shipping-details.model';

describe('Service Tests', () => {
  describe('ShippingDetails Service', () => {
    let injector: TestBed;
    let service: ShippingDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IShippingDetails;
    let expectedResult: IShippingDetails | IShippingDetails[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ShippingDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ShippingDetails(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a ShippingDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.create(new ShippingDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ShippingDetails', () => {
        const returnedFromService = Object.assign(
          {
            userName: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            address3: 'BBBBBB',
            address4: 'BBBBBB',
            address5: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
            userPicture: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of ShippingDetails', () => {
        const returnedFromService = Object.assign(
          {
            userName: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            address3: 'BBBBBB',
            address4: 'BBBBBB',
            address5: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
            userPicture: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a ShippingDetails', () => {
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
