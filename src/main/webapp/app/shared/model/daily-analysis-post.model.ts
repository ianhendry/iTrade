import { Moment } from 'moment';
import { IInstrument } from 'app/shared/model/instrument.model';
import { IMt4Account } from 'app/shared/model/mt-4-account.model';
import { DAYOFWEEK } from 'app/shared/model/enumerations/dayofweek.model';
import { HIGHVOLBARLOCATION } from 'app/shared/model/enumerations/highvolbarlocation.model';

export interface IDailyAnalysisPost {
  id?: number;
  postTitle?: string;
  dateAdded?: Moment;
  dayOfWeek?: DAYOFWEEK;
  backgroundVolume?: string;
  priceAction?: string;
  reasonsToEnter?: string;
  warningSigns?: string;
  dailyChartImageContentType?: string;
  dailyChartImage?: any;
  oneHrChartImageContentType?: string;
  oneHrChartImage?: any;
  fiveMinChartImageContentType?: string;
  fiveMinChartImage?: any;
  planForToday?: string;
  highVolBar?: string;
  highVolBarLocation?: HIGHVOLBARLOCATION;
  instrument?: IInstrument;
  mt4Account?: IMt4Account;
}

export class DailyAnalysisPost implements IDailyAnalysisPost {
  constructor(
    public id?: number,
    public postTitle?: string,
    public dateAdded?: Moment,
    public dayOfWeek?: DAYOFWEEK,
    public backgroundVolume?: string,
    public priceAction?: string,
    public reasonsToEnter?: string,
    public warningSigns?: string,
    public dailyChartImageContentType?: string,
    public dailyChartImage?: any,
    public oneHrChartImageContentType?: string,
    public oneHrChartImage?: any,
    public fiveMinChartImageContentType?: string,
    public fiveMinChartImage?: any,
    public planForToday?: string,
    public highVolBar?: string,
    public highVolBarLocation?: HIGHVOLBARLOCATION,
    public instrument?: IInstrument,
    public mt4Account?: IMt4Account
  ) {}
}
