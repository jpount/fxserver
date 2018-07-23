import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './currency.reducer';
import { ICurrency } from 'app/shared/model/currency.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrencyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class CurrencyDetail extends React.Component<ICurrencyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { currencyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="fxserverApp.currency.detail.title">Currency</Translate> [<b>{currencyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="symbol">
                <Translate contentKey="fxserverApp.currency.symbol">Symbol</Translate>
              </span>
            </dt>
            <dd>{currencyEntity.symbol}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="fxserverApp.currency.name">Name</Translate>
              </span>
            </dt>
            <dd>{currencyEntity.name}</dd>
            <dt>
              <span id="rate">
                <Translate contentKey="fxserverApp.currency.rate">Rate</Translate>
              </span>
            </dt>
            <dd>{currencyEntity.rate}</dd>
          </dl>
          <Button tag={Link} to="/entity/currency" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/currency/${currencyEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ currency }: IRootState) => ({
  currencyEntity: currency.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CurrencyDetail);
