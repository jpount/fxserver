import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CurrencyRate from './currency-rate';
import CurrencyRateDetail from './currency-rate-detail';
import CurrencyRateUpdate from './currency-rate-update';
import CurrencyRateDeleteDialog from './currency-rate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CurrencyRateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CurrencyRateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CurrencyRateDetail} />
      <ErrorBoundaryRoute path={match.url} component={CurrencyRate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CurrencyRateDeleteDialog} />
  </>
);

export default Routes;
