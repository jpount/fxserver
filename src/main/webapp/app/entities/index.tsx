import React from 'react';
import { Switch } from 'react-router-dom';
// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Currency from './currency';
import BankAccount from './bank-account';
import Transaction from './transaction';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/currency`} component={Currency} />
      <ErrorBoundaryRoute path={`${match.url}/bank-account`} component={BankAccount} />
      <ErrorBoundaryRoute path={`${match.url}/transaction`} component={Transaction} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
