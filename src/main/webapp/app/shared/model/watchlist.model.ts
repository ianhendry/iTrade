import { Moment } from 'moment';
import { IInstrument } from 'app/shared/model/instrument.model';

export interface IWatchlist {
  id?: number;
  watchlistName?: string;
  watchlistDescription?: string;
  dateCreated?: Moment;
  dateInactive?: Moment;
  watchlistInactive?: boolean;
  intruments?: IInstrument[];
}

export class Watchlist implements IWatchlist {
  constructor(
    public id?: number,
    public watchlistName?: string,
    public watchlistDescription?: string,
    public dateCreated?: Moment,
    public dateInactive?: Moment,
    public watchlistInactive?: boolean,
    public intruments?: IInstrument[]
  ) {
    this.watchlistInactive = this.watchlistInactive || false;
  }
}
