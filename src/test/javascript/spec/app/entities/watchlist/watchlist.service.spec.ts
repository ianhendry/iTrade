import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { WatchlistService } from 'app/entities/watchlist/watchlist.service';
import { IWatchlist, Watchlist } from 'app/shared/model/watchlist.model';

describe('Service Tests', () => {
  describe('Watchlist Service', () => {
    let injector: TestBed;
    let service: WatchlistService;
    let httpMock: HttpTestingController;
    let elemDefault: IWatchlist;
    let expectedResult: IWatchlist | IWatchlist[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WatchlistService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Watchlist(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreated: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Watchlist', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.create(new Watchlist()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Watchlist', () => {
        const returnedFromService = Object.assign(
          {
            watchlistName: 'BBBBBB',
            watchlistDescription: 'BBBBBB',
            dateCreated: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
            watchlistInactive: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateInactive: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Watchlist', () => {
        const returnedFromService = Object.assign(
          {
            watchlistName: 'BBBBBB',
            watchlistDescription: 'BBBBBB',
            dateCreated: currentDate.format(DATE_FORMAT),
            dateInactive: currentDate.format(DATE_FORMAT),
            watchlistInactive: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
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

      it('should delete a Watchlist', () => {
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
