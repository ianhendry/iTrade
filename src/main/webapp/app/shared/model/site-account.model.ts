import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IMt4Account } from 'app/shared/model/mt-4-account.model';

export interface ISiteAccount {
  id?: number;
  accountEmail?: string;
  accountName?: string;
  accountReal?: boolean;
  accountBalance?: number;
  accountOpenDate?: Moment;
  accountCloseDate?: Moment;
  user?: IUser;
  mt4Account?: IMt4Account;
}

export class SiteAccount implements ISiteAccount {
  constructor(
    public id?: number,
    public accountEmail?: string,
    public accountName?: string,
    public accountReal?: boolean,
    public accountBalance?: number,
    public accountOpenDate?: Moment,
    public accountCloseDate?: Moment,
    public user?: IUser,
    public mt4Account?: IMt4Account
  ) {
    this.accountReal = this.accountReal || false;
  }
}
