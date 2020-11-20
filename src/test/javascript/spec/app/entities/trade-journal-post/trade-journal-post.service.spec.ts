import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TradeJournalPostService } from 'app/entities/trade-journal-post/trade-journal-post.service';
import { ITradeJournalPost, TradeJournalPost } from 'app/shared/model/trade-journal-post.model';

describe('Service Tests', () => {
  describe('TradeJournalPost Service', () => {
    let injector: TestBed;
    let service: TradeJournalPostService;
    let httpMock: HttpTestingController;
    let elemDefault: ITradeJournalPost;
    let expectedResult: ITradeJournalPost | ITradeJournalPost[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TradeJournalPostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TradeJournalPost(
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TradeJournalPost', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate,
          },
          returnedFromService
        );

        service.create(new TradeJournalPost()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TradeJournalPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media1: 'BBBBBB',
            media2: 'BBBBBB',
            media3: 'BBBBBB',
            media4: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TradeJournalPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media1: 'BBBBBB',
            media2: 'BBBBBB',
            media3: 'BBBBBB',
            media4: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
            dateApproved: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TradeJournalPost', () => {
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
