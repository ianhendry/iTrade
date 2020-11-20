import { ISignalSequences } from 'app/shared/model/signal-sequences.model';
import { SIGNALINDICATES } from 'app/shared/model/enumerations/signalindicates.model';

export interface ITradeSignals {
  id?: number;
  name?: string;
  note?: string;
  numberOfBars?: number;
  signalIndicates?: SIGNALINDICATES;
  description?: any;
  background?: any;
  actionToTake?: any;
  sequenceNumber?: number;
  signalsSequences?: ISignalSequences;
}

export class TradeSignals implements ITradeSignals {
  constructor(
    public id?: number,
    public name?: string,
    public note?: string,
    public numberOfBars?: number,
    public signalIndicates?: SIGNALINDICATES,
    public description?: any,
    public background?: any,
    public actionToTake?: any,
    public sequenceNumber?: number,
    public signalsSequences?: ISignalSequences
  ) {}
}
