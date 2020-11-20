import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVideoPost, VideoPost } from 'app/shared/model/video-post.model';
import { VideoPostService } from './video-post.service';
import { VideoPostComponent } from './video-post.component';
import { VideoPostDetailComponent } from './video-post-detail.component';
import { VideoPostUpdateComponent } from './video-post-update.component';

@Injectable({ providedIn: 'root' })
export class VideoPostResolve implements Resolve<IVideoPost> {
  constructor(private service: VideoPostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVideoPost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((videoPost: HttpResponse<VideoPost>) => {
          if (videoPost.body) {
            return of(videoPost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VideoPost());
  }
}

export const videoPostRoute: Routes = [
  {
    path: '',
    component: VideoPostComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.videoPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VideoPostDetailComponent,
    resolve: {
      videoPost: VideoPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.videoPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VideoPostUpdateComponent,
    resolve: {
      videoPost: VideoPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.videoPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VideoPostUpdateComponent,
    resolve: {
      videoPost: VideoPostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'iTradeApp.videoPost.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
