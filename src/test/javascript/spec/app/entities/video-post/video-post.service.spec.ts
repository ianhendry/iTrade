import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VideoPostService } from 'app/entities/video-post/video-post.service';
import { IVideoPost, VideoPost } from 'app/shared/model/video-post.model';

describe('Service Tests', () => {
  describe('VideoPost Service', () => {
    let injector: TestBed;
    let service: VideoPostService;
    let httpMock: HttpTestingController;
    let elemDefault: IVideoPost;
    let expectedResult: IVideoPost | IVideoPost[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VideoPostService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new VideoPost(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a VideoPost', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
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

        service.create(new VideoPost()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VideoPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media: 'BBBBBB',
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

      it('should return a list of VideoPost', () => {
        const returnedFromService = Object.assign(
          {
            postTitle: 'BBBBBB',
            postBody: 'BBBBBB',
            dateAdded: currentDate.format(DATE_TIME_FORMAT),
            dateApproved: currentDate.format(DATE_FORMAT),
            media: 'BBBBBB',
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

      it('should delete a VideoPost', () => {
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
