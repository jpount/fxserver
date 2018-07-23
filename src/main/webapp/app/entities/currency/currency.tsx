import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './currency.reducer';
import { ICurrency } from 'app/shared/model/currency.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrencyProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Currency extends React.Component<ICurrencyProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { currencyList, match } = this.props;
    return (
      <div>
        <h2 id="currency-heading">
          <Translate contentKey="fxserverApp.currency.home.title">Currencies</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="fxserverApp.currency.home.createLabel">Create new Currency</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="fxserverApp.currency.symbol">Symbol</Translate>
                </th>
                <th>
                  <Translate contentKey="fxserverApp.currency.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="fxserverApp.currency.rate">Rate</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {currencyList.map((currency, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${currency.id}`} color="link" size="sm">
                      {currency.id}
                    </Button>
                  </td>
                  <td>{currency.symbol}</td>
                  <td>{currency.name}</td>
                  <td>{currency.rate}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${currency.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currency.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${currency.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ currency }: IRootState) => ({
  currencyList: currency.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Currency);
