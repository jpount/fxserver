import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './transaction.reducer';

// tslint:disable-next-line:no-unused-variable

export interface ITransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class TransactionDetail extends React.Component<ITransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { transactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="fxserverApp.transaction.detail.title">Transaction</Translate> [<b>{transactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fromAmount">
                <Translate contentKey="fxserverApp.transaction.fromAmount">From Amount</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.fromAmount}</dd>
            <dt>
              <span id="toAmount">
                <Translate contentKey="fxserverApp.transaction.toAmount">To Amount</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.toAmount}</dd>
            <dt>
              <span id="feeAmount">
                <Translate contentKey="fxserverApp.transaction.feeAmount">Fee Amount</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.feeAmount}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="fxserverApp.transaction.state">State</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.state}</dd>
            <dt>
              <span id="stateDescription">
                <Translate contentKey="fxserverApp.transaction.stateDescription">State Description</Translate>
              </span>
            </dt>
            <dd>{transactionEntity.stateDescription}</dd>
            <dt>
              <Translate contentKey="fxserverApp.transaction.from">From</Translate>
            </dt>
            <dd>{transactionEntity.from ? transactionEntity.from.bsb : ''}</dd>
            <dt>
              <Translate contentKey="fxserverApp.transaction.to">To</Translate>
            </dt>
            <dd>{transactionEntity.to ? transactionEntity.to.bsb : ''}</dd>
            <dt>
              <Translate contentKey="fxserverApp.transaction.feeCurrency">Fee Currency</Translate>
            </dt>
            <dd>{transactionEntity.feeCurrency ? transactionEntity.feeCurrency.symbol : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/transaction/${transactionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ transaction }: IRootState) => ({
  transactionEntity: transaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionDetail);
