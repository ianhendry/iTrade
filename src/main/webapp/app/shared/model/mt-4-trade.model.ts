import { Moment } from 'moment';
import { ITradeJournalPost } from 'app/shared/model/trade-journal-post.model';
import { IInstrument } from 'app/shared/model/instrument.model';

export interface IMt4Trade {
  id?: number;
  ticket?: number;
  openTime?: Moment;
  directionType?: string;
  positionSize?: number;
  openPrice?: number;
  stopLossPrice?: number;
  takeProfitPrice?: number;
  closeTime?: Moment;
  closePrice?: number;
  commission?: number;
  taxes?: number;
  swap?: number;
  profit?: number;
  tradeJournalPost?: ITradeJournalPost;
  instrument?: IInstrument;
}

export class Mt4Trade implements IMt4Trade {
  constructor(
    public id?: number,
    public ticket?: number,
    public openTime?: Moment,
    public directionType?: string,
    public positionSize?: number,
    public openPrice?: number,
    public stopLossPrice?: number,
    public takeProfitPrice?: number,
    public closeTime?: Moment,
    public closePrice?: number,
    public commission?: number,
    public taxes?: number,
    public swap?: number,
    public profit?: number,
    public tradeJournalPost?: ITradeJournalPost,
    public instrument?: IInstrument
  ) {}
}
