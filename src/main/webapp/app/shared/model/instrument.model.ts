import { Moment } from 'moment';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { ISignalService } from 'app/shared/model/signal-service.model';
import { DATAPROVIDER } from 'app/shared/model/enumerations/dataprovider.model';

export interface IInstrument {
  id?: number;
  dataProvider?: DATAPROVIDER;
  ticker?: string;
  exchange?: string;
  description?: string;
  dataFrom?: Moment;
  isActive?: boolean;
  dateAdded?: Moment;
  dateInactive?: Moment;
  watchlists?: IWatchlist[];
  signalServices?: ISignalService[];
}

export class Instrument implements IInstrument {
  constructor(
    public id?: number,
    public dataProvider?: DATAPROVIDER,
    public ticker?: string,
    public exchange?: string,
    public description?: string,
    public dataFrom?: Moment,
    public isActive?: boolean,
    public dateAdded?: Moment,
    public dateInactive?: Moment,
    public watchlists?: IWatchlist[],
    public signalServices?: ISignalService[]
  ) {
    this.isActive = this.isActive || false;
  }
}
