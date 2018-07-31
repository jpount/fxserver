import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './currency-rate.reducer';

// tslint:disable-next-line:no-unused-variable

export interface ICurrencyRateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class CurrencyRateDetail extends React.Component<ICurrencyRateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { currencyRateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="fxserverApp.currencyRate.detail.title">CurrencyRate</Translate> [<b>{currencyRateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="rate">
                <Translate contentKey="fxserverApp.currencyRate.rate">Rate</Translate>
              </span>
            </dt>
            <dd>{currencyRateEntity.rate}</dd>
            <dt>
              <Translate contentKey="fxserverApp.currencyRate.currency">Currency</Translate>
            </dt>
            <dd>{currencyRateEntity.currency ? currencyRateEntity.currency.symbol : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/currency-rate" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/currency-rate/${currencyRateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ currencyRate }: IRootState) => ({
  currencyRateEntity: currencyRate.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CurrencyRateDetail);
