import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DailyAnalysisPostService } from 'app/entities/daily-analysis-post/daily-analysis-post.service';
import { IDailyAnalysisPost, DailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';
import { DAYOFWEEK } from 'app/shared/model/enumerations/dayofweek.model';
import { HIGHVOLBARLOCATION } from 'app/shared/model/enumerations/highvolbarlocation.model';

describe('Service Tests', () => {
  describe('DailyAnalysisPost Service', () => {
    let injector: TestBed;
    let service: DailyAnalysisPostService;
    let httpMock: HttpTestingController;
    let elemDefault: IDailyAnalysisPost;
    let expectedResult: IDailyAnalysisPost | IDailyAnalysisPost[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DailyAnalysisPostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DailyAnalysisPost(
        0,
        'AAAAAAA',
        currentDate,
        DAYOFWEEK.MONDAY,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        HIGHVOLBARLOCATION.TOUCHING
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DailyAnalysisPost', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
          },
          returnedFromService
        );

        service.create(new DailyAnalysisPost()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DailyAnalysisPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
            dayOfWeek: 'BBBBBB',
            backgroundVolume: 'BBBBBB',
            priceAction: 'BBBBBB',
            reasonsToEnter: 'BBBBBB',
            warningSigns: 'BBBBBB',
            dailyChartImage: 'BBBBBB',
            oneHrChartImage: 'BBBBBB',
            fiveMinChartImage: 'BBBBBB',
            planForToday: 'BBBBBB',
            highVolBar: 'BBBBBB',
            highVolBarLocation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DailyAnalysisPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
            dayOfWeek: 'BBBBBB',
            backgroundVolume: 'BBBBBB',
            priceAction: 'BBBBBB',
            reasonsToEnter: 'BBBBBB',
            warningSigns: 'BBBBBB',
            dailyChartImage: 'BBBBBB',
            oneHrChartImage: 'BBBBBB',
            fiveMinChartImage: 'BBBBBB',
            planForToday: 'BBBBBB',
            highVolBar: 'BBBBBB',
            highVolBarLocation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAdded: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DailyAnalysisPost', () => {
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
