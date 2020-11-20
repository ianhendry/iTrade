import { Moment } from 'moment';
import { ISignalService } from 'app/shared/model/signal-service.model';
import { IInstrument } from 'app/shared/model/instrument.model';
import { TIMEFRAME } from 'app/shared/model/enumerations/timeframe.model';

export interface IPriceDataHistory {
  id?: number;
  priceTimeframe?: TIMEFRAME;
  priceDate?: Moment;
  priceTime?: Moment;
  priceOpen?: number;
  priceHigh?: number;
  priceLow?: number;
  priceClose?: number;
  priceVolume?: number;
  signalServices?: ISignalService[];
  instrument?: IInstrument;
}

export class PriceDataHistory implements IPriceDataHistory {
  constructor(
    public id?: number,
    public priceTimeframe?: TIMEFRAME,
    public priceDate?: Moment,
    public priceTime?: Moment,
    public priceOpen?: number,
    public priceHigh?: number,
    public priceLow?: number,
    public priceClose?: number,
    public priceVolume?: number,
    public signalServices?: ISignalService[],
    public instrument?: IInstrument
  ) {}
}
