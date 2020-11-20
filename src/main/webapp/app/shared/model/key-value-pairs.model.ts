export interface IKeyValuePairs {
  id?: number;
  keyValueGroup?: string;
  keyValue?: string;
  keyValueEntry?: string;
}

export class KeyValuePairs implements IKeyValuePairs {
  constructor(public id?: number, public keyValueGroup?: string, public keyValue?: string, public keyValueEntry?: string) {}
}
