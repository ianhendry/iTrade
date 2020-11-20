import { Moment } from 'moment';
import { IDailyAnalysisPost } from 'app/shared/model/daily-analysis-post.model';
import { IMt4Trade } from 'app/shared/model/mt-4-trade.model';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { ACCOUNTTYPE } from 'app/shared/model/enumerations/accounttype.model';
import { BROKER } from 'app/shared/model/enumerations/broker.model';

export interface IMt4Account {
  id?: number;
  accountType?: ACCOUNTTYPE;
  accountBroker?: BROKER;
  accountLogin?: string;
  accountPassword?: string;
  accountActive?: boolean;
  accountCloseDate?: Moment;
  dailyAnalysisPosts?: IDailyAnalysisPost[];
  mt4Trade?: IMt4Trade;
  watchlist?: IWatchlist;
}

export class Mt4Account implements IMt4Account {
  constructor(
    public id?: number,
    public accountType?: ACCOUNTTYPE,
    public accountBroker?: BROKER,
    public accountLogin?: string,
    public accountPassword?: string,
    public accountActive?: boolean,
    public accountCloseDate?: Moment,
    public dailyAnalysisPosts?: IDailyAnalysisPost[],
    public mt4Trade?: IMt4Trade,
    public watchlist?: IWatchlist
  ) {
    this.accountActive = this.accountActive || false;
  }
}
