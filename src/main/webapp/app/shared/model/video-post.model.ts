import { Moment } from 'moment';

export interface IVideoPost {
  id?: number;
  postTitle?: string;
  postBody?: string;
  dateAdded?: Moment;
  dateApproved?: Moment;
  mediaContentType?: string;
  media?: any;
}

export class VideoPost implements IVideoPost {
  constructor(
    public id?: number,
    public postTitle?: string,
    public postBody?: string,
    public dateAdded?: Moment,
    public dateApproved?: Moment,
    public mediaContentType?: string,
    public media?: any
  ) {}
}
