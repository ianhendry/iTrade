import { Moment } from 'moment';
import { ITradeSignals } from 'app/shared/model/trade-signals.model';
import { IInstrument } from 'app/shared/model/instrument.model';
import { IPriceDataHistory } from 'app/shared/model/price-data-history.model';
import { SIGNALINDICATES } from 'app/shared/model/enumerations/signalindicates.model';
import { TIMEFRAME } from 'app/shared/model/enumerations/timeframe.model';
import { SIGNALBARSIZE } from 'app/shared/model/enumerations/signalbarsize.model';
import { BARCLOSE } from 'app/shared/model/enumerations/barclose.model';

export interface ISignalService {
  id?: number;
  alertDate?: Moment;
  alertTime?: Moment;
  ticker?: string;
  alertText?: string;
  alertDescription?: any;
  signalIndicates?: SIGNALINDICATES;
  imageContentType?: string;
  image?: any;
  timeframe?: TIMEFRAME;
  alertPrice?: number;
  isSequencePresent?: boolean;
  barVolume?: number;
  barSize?: SIGNALBARSIZE;
  barClose?: BARCLOSE;
  isPublished?: boolean;
  tradeSignals?: ITradeSignals;
  intruments?: IInstrument[];
  priceDataHistory?: IPriceDataHistory;
}

export class SignalService implements ISignalService {
  constructor(
    public id?: number,
    public alertDate?: Moment,
    public alertTime?: Moment,
    public ticker?: string,
    public alertText?: string,
    public alertDescription?: any,
    public signalIndicates?: SIGNALINDICATES,
    public imageContentType?: string,
    public image?: any,
    public timeframe?: TIMEFRAME,
    public alertPrice?: number,
    public isSequencePresent?: boolean,
    public barVolume?: number,
    public barSize?: SIGNALBARSIZE,
    public barClose?: BARCLOSE,
    public isPublished?: boolean,
    public tradeSignals?: ITradeSignals,
    public intruments?: IInstrument[],
    public priceDataHistory?: IPriceDataHistory
  ) {
    this.isSequencePresent = this.isSequencePresent || false;
    this.isPublished = this.isPublished || false;
  }
}
