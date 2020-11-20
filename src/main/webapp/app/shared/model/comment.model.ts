import { Moment } from 'moment';

export interface IComment {
  id?: number;
  commentTitle?: string;
  commentBody?: string;
  commentMediaContentType?: string;
  commentMedia?: any;
  dateAdded?: Moment;
  dateApproved?: Moment;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public commentTitle?: string,
    public commentBody?: string,
    public commentMediaContentType?: string,
    public commentMedia?: any,
    public dateAdded?: Moment,
    public dateApproved?: Moment
  ) {}
}
