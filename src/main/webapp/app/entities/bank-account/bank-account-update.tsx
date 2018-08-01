import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICurrency } from 'app/shared/model/currency.model';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-account.reducer';
import { IBankAccount } from 'app/shared/model/bank-account.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IBankAccountUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IBankAccountUpdateState {
  isNew: boolean;
  currencyId: number;
  userId: number;
}

export class BankAccountUpdate extends React.Component<IBankAccountUpdateProps, IBankAccountUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      currencyId: 0,
      userId: 0,
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
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bankAccountEntity } = this.props;
      const entity = {
        ...bankAccountEntity,
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
    this.props.history.push('/entity/bank-account');
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

  userUpdate = element => {
    const login = element.target.value.toString();
    if (login === '') {
      this.setState({
        userId: -1
      });
    } else {
      for (const i in this.props.users) {
        if (login === this.props.users[i].login.toString()) {
          this.setState({
            userId: this.props.users[i].id
          });
        }
      }
    }
  };

  render() {
    const { bankAccountEntity, currencies, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fxserverApp.bankAccount.home.createOrEditLabel">
              <Translate contentKey="fxserverApp.bankAccount.home.createOrEditLabel">Create or edit a BankAccount</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bankAccountEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bank-account-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="bsbLabel" for="bsb">
                    <Translate contentKey="fxserverApp.bankAccount.bsb">Bsb</Translate>
                  </Label>
                  <AvField
                    id="bank-account-bsb"
                    type="text"
                    name="bsb"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 6, errorMessage: translate('entity.validation.minlength', { min: 6 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="bicLabel" for="bic">
                    <Translate contentKey="fxserverApp.bankAccount.bic">Bic</Translate>
                  </Label>
                  <AvField
                    id="bank-account-bic"
                    type="text"
                    name="bic"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 8, errorMessage: translate('entity.validation.minlength', { min: 8 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="fxserverApp.bankAccount.name">Name</Translate>
                  </Label>
                  <AvField id="bank-account-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="amountLabel" for="amount">
                    <Translate contentKey="fxserverApp.bankAccount.amount">Amount</Translate>
                  </Label>
                  <AvField
                    id="bank-account-amount"
                    type="text"
                    name="amount"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel">
                    <Translate contentKey="fxserverApp.bankAccount.state">State</Translate>
                  </Label>
                  <AvInput
                    id="bank-account-state"
                    type="select"
                    className="form-control"
                    name="state"
                    value={(!isNew && bankAccountEntity.state) || 'CREATED'}
                  >
                    <option value="CREATED">
                      <Translate contentKey="fxserverApp.BackAccountState.CREATED" />
                    </option>
                    <option value="ACTIVE">
                      <Translate contentKey="fxserverApp.BackAccountState.ACTIVE" />
                    </option>
                    <option value="BLOCKED">
                      <Translate contentKey="fxserverApp.BackAccountState.BLOCKED" />
                    </option>
                    <option value="CLOSED">
                      <Translate contentKey="fxserverApp.BackAccountState.CLOSED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="stateDescriptionLabel" for="stateDescription">
                    <Translate contentKey="fxserverApp.bankAccount.stateDescription">State Description</Translate>
                  </Label>
                  <AvField id="bank-account-stateDescription" type="text" name="stateDescription" />
                </AvGroup>
                <AvGroup>
                  <Label id="numberLabel" for="number">
                    <Translate contentKey="fxserverApp.bankAccount.number">Number</Translate>
                  </Label>
                  <AvField
                    id="bank-account-number"
                    type="text"
                    name="number"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 4, errorMessage: translate('entity.validation.minlength', { min: 4 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="currency.symbol">
                    <Translate contentKey="fxserverApp.bankAccount.currency">Currency</Translate>
                  </Label>
                  <AvInput
                    id="bank-account-currency"
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
                <AvGroup>
                  <Label for="user.login">
                    <Translate contentKey="fxserverApp.bankAccount.user">User</Translate>
                  </Label>
                  <AvInput id="bank-account-user" type="select" className="form-control" name="user.login" onChange={this.userUpdate}>
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.login} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/bank-account" replace color="info">
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
  users: storeState.userManagement.users,
  bankAccountEntity: storeState.bankAccount.entity,
  loading: storeState.bankAccount.loading,
  updating: storeState.bankAccount.updating
});

const mapDispatchToProps = {
  getCurrencies,
  getUsers,
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
)(BankAccountUpdate);
