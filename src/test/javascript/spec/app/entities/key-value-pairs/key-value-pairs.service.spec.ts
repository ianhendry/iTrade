import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { KeyValuePairsService } from 'app/entities/key-value-pairs/key-value-pairs.service';
import { IKeyValuePairs, KeyValuePairs } from 'app/shared/model/key-value-pairs.model';

describe('Service Tests', () => {
  describe('KeyValuePairs Service', () => {
    let injector: TestBed;
    let service: KeyValuePairsService;
    let httpMock: HttpTestingController;
    let elemDefault: IKeyValuePairs;
    let expectedResult: IKeyValuePairs | IKeyValuePairs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(KeyValuePairsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new KeyValuePairs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a KeyValuePairs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new KeyValuePairs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a KeyValuePairs', () => {
        const returnedFromService = Object.assign(
          {
            keyValueGroup: 'BBBBBB',
            keyValue: 'BBBBBB',
            keyValueEntry: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of KeyValuePairs', () => {
        const returnedFromService = Object.assign(
          {
            keyValueGroup: 'BBBBBB',
            keyValue: 'BBBBBB',
            keyValueEntry: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a KeyValuePairs', () => {
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
