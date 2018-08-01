import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bank-account.reducer';
import { IBankAccount } from 'app/shared/model/bank-account.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBankAccountDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class BankAccountDetail extends React.Component<IBankAccountDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bankAccountEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="fxserverApp.bankAccount.detail.title">BankAccount</Translate> [<b>{bankAccountEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="bsb">
                <Translate contentKey="fxserverApp.bankAccount.bsb">Bsb</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.bsb}</dd>
            <dt>
              <span id="bic">
                <Translate contentKey="fxserverApp.bankAccount.bic">Bic</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.bic}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="fxserverApp.bankAccount.name">Name</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.name}</dd>
            <dt>
              <span id="amount">
                <Translate contentKey="fxserverApp.bankAccount.amount">Amount</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.amount}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="fxserverApp.bankAccount.state">State</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.state}</dd>
            <dt>
              <span id="stateDescription">
                <Translate contentKey="fxserverApp.bankAccount.stateDescription">State Description</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.stateDescription}</dd>
            <dt>
              <span id="number">
                <Translate contentKey="fxserverApp.bankAccount.number">Number</Translate>
              </span>
            </dt>
            <dd>{bankAccountEntity.number}</dd>
            <dt>
              <Translate contentKey="fxserverApp.bankAccount.currency">Currency</Translate>
            </dt>
            <dd>{bankAccountEntity.currency ? bankAccountEntity.currency.symbol : ''}</dd>
            <dt>
              <Translate contentKey="fxserverApp.bankAccount.user">User</Translate>
            </dt>
            <dd>{bankAccountEntity.user ? bankAccountEntity.user.login : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/bank-account" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/bank-account/${bankAccountEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ bankAccount }: IRootState) => ({
  bankAccountEntity: bankAccount.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankAccountDetail);
