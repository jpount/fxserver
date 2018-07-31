import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { createEntity, getEntity, reset, updateEntity } from './currency-rate.reducer';

// tslint:disable-next-line:no-unused-variable

export interface ICurrencyRateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ICurrencyRateUpdateState {
  isNew: boolean;
  currencyId: number;
}

export class CurrencyRateUpdate extends React.Component<ICurrencyRateUpdateProps, ICurrencyRateUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      currencyId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCurrencies();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { currencyRateEntity } = this.props;
      const entity = {
        ...currencyRateEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/currency-rate');
  };

  currencyUpdate = element => {
    const symbol = element.target.value.toString();
    if (symbol === '') {
      this.setState({
        currencyId: -1
      });
    } else {
      for (const i in this.props.currencies) {
        if (symbol === this.props.currencies[i].symbol.toString()) {
          this.setState({
            currencyId: this.props.currencies[i].id
          });
        }
      }
    }
  };

  render() {
    const { currencyRateEntity, currencies, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fxserverApp.currencyRate.home.createOrEditLabel">
              <Translate contentKey="fxserverApp.currencyRate.home.createOrEditLabel">Create or edit a CurrencyRate</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : currencyRateEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="currency-rate-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="rateLabel" for="rate">
                    <Translate contentKey="fxserverApp.currencyRate.rate">Rate</Translate>
                  </Label>
                  <AvField
                    id="currency-rate-rate"
                    type="text"
                    name="rate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="currency.symbol">
                    <Translate contentKey="fxserverApp.currencyRate.currency">Currency</Translate>
                  </Label>
                  <AvInput
                    id="currency-rate-currency"
                    type="select"
                    className="form-control"
                    name="currency.id"
                    onChange={this.currencyUpdate}
                    value={isNew && currencies ? currencies[0] && currencies[0].id : ''}
                  >
                    {currencies
                      ? currencies.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.symbol}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/currency-rate" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  currencies: storeState.currency.entities,
  currencyRateEntity: storeState.currencyRate.entity,
  loading: storeState.currencyRate.loading,
  updating: storeState.currencyRate.updating
});

const mapDispatchToProps = {
  getCurrencies,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CurrencyRateUpdate);
