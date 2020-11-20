export interface ISignalSequences {
  id?: number;
  sequenceName?: string;
  sequenceIdentifier?: string;
  sequenceDescription?: any;
}

export class SignalSequences implements ISignalSequences {
  constructor(public id?: number, public sequenceName?: string, public sequenceIdentifier?: string, public sequenceDescription?: any) {}
}
