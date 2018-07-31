import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { getSortState, IPaginationBaseState, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './bank-account.reducer';
// tslint:disable-next-line:no-unused-variable
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IBankAccountProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IBankAccountState = IPaginationBaseState;

export class BankAccount extends React.Component<IBankAccountProps, IBankAccountState> {
  state: IBankAccountState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  reset = () => {
    this.setState({ activePage: 0 }, () => {
      this.props.reset();
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        activePage: 0,
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.reset()
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { bankAccountList, match } = this.props;
    return (
      <div>
        <h2 id="bank-account-heading">
          <Translate contentKey="fxserverApp.bankAccount.home.title">Bank Accounts</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="fxserverApp.bankAccount.home.createLabel">Create new Bank Account</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('bsb')}>
                    <Translate contentKey="fxserverApp.bankAccount.bsb">Bsb</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('bic')}>
                    <Translate contentKey="fxserverApp.bankAccount.bic">Bic</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('name')}>
                    <Translate contentKey="fxserverApp.bankAccount.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('amount')}>
                    <Translate contentKey="fxserverApp.bankAccount.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('state')}>
                    <Translate contentKey="fxserverApp.bankAccount.state">State</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('stateDescription')}>
                    <Translate contentKey="fxserverApp.bankAccount.stateDescription">State Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fxserverApp.bankAccount.currency">Currency</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="fxserverApp.bankAccount.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bankAccountList.map((bankAccount, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${bankAccount.id}`} color="link" size="sm">
                        {bankAccount.id}
                      </Button>
                    </td>
                    <td>{bankAccount.bsb}</td>
                    <td>{bankAccount.bic}</td>
                    <td>{bankAccount.name}</td>
                    <td>{bankAccount.amount}</td>
                    <td>
                      <Translate contentKey={`fxserverApp.BackAccountState.${bankAccount.state}`} />
                    </td>
                    <td>{bankAccount.stateDescription}</td>
                    <td>
                      {bankAccount.currency ? <Link to={`currency/${bankAccount.currency.id}`}>{bankAccount.currency.symbol}</Link> : ''}
                    </td>
                    <td>{bankAccount.user ? bankAccount.user.login : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${bankAccount.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankAccount.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${bankAccount.id}/delete`} color="danger" size="sm">
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
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ bankAccount }: IRootState) => ({
  bankAccountList: bankAccount.entities,
  totalItems: bankAccount.totalItems,
  links: bankAccount.links
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankAccount);
