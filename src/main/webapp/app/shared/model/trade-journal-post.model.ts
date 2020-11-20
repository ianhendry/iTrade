import { Moment } from 'moment';
import { IComment } from 'app/shared/model/comment.model';
import { IVideoPost } from 'app/shared/model/video-post.model';

export interface ITradeJournalPost {
  id?: number;
  postTitle?: string;
  postBody?: string;
  dateAdded?: Moment;
  dateApproved?: Moment;
  media1ContentType?: string;
  media1?: any;
  media2ContentType?: string;
  media2?: any;
  media3ContentType?: string;
  media3?: any;
  media4ContentType?: string;
  media4?: any;
  comment?: IComment;
  videoPost?: IVideoPost;
}

export class TradeJournalPost implements ITradeJournalPost {
  constructor(
    public id?: number,
    public postTitle?: string,
    public postBody?: string,
    public dateAdded?: Moment,
    public dateApproved?: Moment,
    public media1ContentType?: string,
    public media1?: any,
    public media2ContentType?: string,
    public media2?: any,
    public media3ContentType?: string,
    public media3?: any,
    public media4ContentType?: string,
    public media4?: any,
    public comment?: IComment,
    public videoPost?: IVideoPost
  ) {}
}
