import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'price-data-history',
        loadChildren: () => import('./price-data-history/price-data-history.module').then(m => m.ITradePriceDataHistoryModule),
      },
      {
        path: 'key-value-pairs',
        loadChildren: () => import('./key-value-pairs/key-value-pairs.module').then(m => m.ITradeKeyValuePairsModule),
      },
      {
        path: 'signal-sequences',
        loadChildren: () => import('./signal-sequences/signal-sequences.module').then(m => m.ITradeSignalSequencesModule),
      },
      {
        path: 'trade-signals',
        loadChildren: () => import('./trade-signals/trade-signals.module').then(m => m.ITradeTradeSignalsModule),
      },
      {
        path: 'signal-service',
        loadChildren: () => import('./signal-service/signal-service.module').then(m => m.ITradeSignalServiceModule),
      },
      {
        path: 'site-account',
        loadChildren: () => import('./site-account/site-account.module').then(m => m.ITradeSiteAccountModule),
      },
      {
        path: 'mt-4-account',
        loadChildren: () => import('./mt-4-account/mt-4-account.module').then(m => m.ITradeMt4AccountModule),
      },
      {
        path: 'shipping-details',
        loadChildren: () => import('./shipping-details/shipping-details.module').then(m => m.ITradeShippingDetailsModule),
      },
      {
        path: 'trade-journal-post',
        loadChildren: () => import('./trade-journal-post/trade-journal-post.module').then(m => m.ITradeTradeJournalPostModule),
      },
      {
        path: 'daily-analysis-post',
        loadChildren: () => import('./daily-analysis-post/daily-analysis-post.module').then(m => m.ITradeDailyAnalysisPostModule),
      },
      {
        path: 'video-post',
        loadChildren: () => import('./video-post/video-post.module').then(m => m.ITradeVideoPostModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.ITradeCommentModule),
      },
      {
        path: 'watchlist',
        loadChildren: () => import('./watchlist/watchlist.module').then(m => m.ITradeWatchlistModule),
      },
      {
        path: 'instrument',
        loadChildren: () => import('./instrument/instrument.module').then(m => m.ITradeInstrumentModule),
      },
      {
        path: 'mt-4-trade',
        loadChildren: () => import('./mt-4-trade/mt-4-trade.module').then(m => m.ITradeMt4TradeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ITradeEntityModule {}
