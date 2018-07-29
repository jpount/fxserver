import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getBankAccounts } from 'app/entities/bank-account/bank-account.reducer';
import { getEntities as getCurrencies } from 'app/entities/currency/currency.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './transaction.reducer';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';

export interface ITransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface ITransactionUpdateState {
  isNew: boolean;
  fromId: number;
  toId: number;
  feeCurrencyId: number;
  createdById: number;
  updatedById: number;
}

export class TransactionUpdate extends React.Component<ITransactionUpdateProps, ITransactionUpdateState> {
  saveEntity = (event, errors, values) => {
    values.createdAt = new Date(values.createdAt);
    values.updatedAt = new Date(values.updatedAt);

    if (errors.length === 0) {
      const { transactionEntity } = this.props;
      const entity = {
        ...transactionEntity,
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
    this.props.history.push('/entity/transaction');
  };
  fromUpdate = element => {
    const bsb = element.target.value.toString();
    if (bsb === '') {
      this.setState({
        fromId: -1
      });
    } else {
      for (const i in this.props.bankAccounts) {
        if (bsb === this.props.bankAccounts[i].bsb.toString()) {
          this.setState({
            fromId: this.props.bankAccounts[i].id
          });
        }
      }
    }
  };
  toUpdate = element => {
    const bsb = element.target.value.toString();
    if (bsb === '') {
      this.setState({
        toId: -1
      });
    } else {
      for (const i in this.props.bankAccounts) {
        if (bsb === this.props.bankAccounts[i].bsb.toString()) {
          this.setState({
            toId: this.props.bankAccounts[i].id
          });
        }
      }
    }
  };
  feeCurrencyUpdate = element => {
    const symbol = element.target.value.toString();
    if (symbol === '') {
      this.setState({
        feeCurrencyId: -1
      });
    } else {
      for (const i in this.props.currencies) {
        if (symbol === this.props.currencies[i].symbol.toString()) {
          this.setState({
            feeCurrencyId: this.props.currencies[i].id
          });
        }
      }
    }
  };
  createdByUpdate = element => {
    const login = element.target.value.toString();
    if (login === '') {
      this.setState({
        createdById: -1
      });
    } else {
      for (const i in this.props.users) {
        if (login === this.props.users[i].login.toString()) {
          this.setState({
            createdById: this.props.users[i].id
          });
        }
      }
    }
  };
  updatedByUpdate = element => {
    const login = element.target.value.toString();
    if (login === '') {
      this.setState({
        updatedById: -1
      });
    } else {
      for (const i in this.props.users) {
        if (login === this.props.users[i].login.toString()) {
          this.setState({
            updatedById: this.props.users[i].id
          });
        }
      }
    }
  };

  constructor(props) {
    super(props);
    this.state = {
      fromId: 0,
      toId: 0,
      feeCurrencyId: 0,
      createdById: 0,
      updatedById: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getBankAccounts();
    this.props.getCurrencies();
    this.props.getUsers();
  }

  render() {
    const { transactionEntity, bankAccounts, currencies, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fxserverApp.transaction.home.createOrEditLabel">
              <Translate contentKey="fxserverApp.transaction.home.createOrEditLabel">Create or edit a Transaction</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : transactionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="transaction-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fromAmountLabel" for="fromAmount">
                    <Translate contentKey="fxserverApp.transaction.fromAmount">From Amount</Translate>
                  </Label>
                  <AvField
                    id="transaction-fromAmount"
                    type="text"
                    name="fromAmount"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="toAmountLabel" for="toAmount">
                    <Translate contentKey="fxserverApp.transaction.toAmount">To Amount</Translate>
                  </Label>
                  <AvField
                    id="transaction-toAmount"
                    type="text"
                    name="toAmount"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="feeAmountLabel" for="feeAmount">
                    <Translate contentKey="fxserverApp.transaction.feeAmount">Fee Amount</Translate>
                  </Label>
                  <AvField id="transaction-feeAmount" type="text" name="feeAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel">
                    <Translate contentKey="fxserverApp.transaction.state">State</Translate>
                  </Label>
                  <AvInput
                    id="transaction-state"
                    type="select"
                    className="form-control"
                    name="state"
                    value={(!isNew && transactionEntity.state) || 'CREATED'}
                  >
                    <option value="CREATED">
                      <Translate contentKey="fxserverApp.TransactionState.CREATED" />
                    </option>
                    <option value="PENDING">
                      <Translate contentKey="fxserverApp.TransactionState.PENDING" />
                    </option>
                    <option value="PROCESSED">
                      <Translate contentKey="fxserverApp.TransactionState.PROCESSED" />
                    </option>
                    <option value="FAILED">
                      <Translate contentKey="fxserverApp.TransactionState.FAILED" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="stateDescriptionLabel" for="stateDescription">
                    <Translate contentKey="fxserverApp.transaction.stateDescription">State Description</Translate>
                  </Label>
                  <AvField id="transaction-stateDescription" type="text" name="stateDescription" />
                </AvGroup>
                <AvGroup>
                  <Label id="createdAtLabel" for="createdAt">
                    <Translate contentKey="fxserverApp.transaction.createdAt">Created At</Translate>
                  </Label>
                  <AvInput
                    id="transaction-createdAt"
                    type="datetime-local"
                    className="form-control"
                    name="createdAt"
                    value={isNew ? null : convertDateTimeFromServer(this.props.transactionEntity.createdAt)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="updatedAtLabel" for="updatedAt">
                    <Translate contentKey="fxserverApp.transaction.updatedAt">Updated At</Translate>
                  </Label>
                  <AvInput
                    id="transaction-updatedAt"
                    type="datetime-local"
                    className="form-control"
                    name="updatedAt"
                    value={isNew ? null : convertDateTimeFromServer(this.props.transactionEntity.updatedAt)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="from.bsb">
                    <Translate contentKey="fxserverApp.transaction.from">From</Translate>
                  </Label>
                  <AvInput
                    id="transaction-from"
                    type="select"
                    className="form-control"
                    name="from.id"
                    onChange={this.fromUpdate}
                    value={isNew && bankAccounts ? bankAccounts[0] && bankAccounts[0].id : ''}
                  >
                    {bankAccounts
                      ? bankAccounts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.bsb}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="to.bsb">
                    <Translate contentKey="fxserverApp.transaction.to">To</Translate>
                  </Label>
                  <AvInput
                    id="transaction-to"
                    type="select"
                    className="form-control"
                    name="to.id"
                    onChange={this.toUpdate}
                    value={isNew && bankAccounts ? bankAccounts[0] && bankAccounts[0].id : ''}
                  >
                    {bankAccounts
                      ? bankAccounts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.bsb}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="feeCurrency.symbol">
                    <Translate contentKey="fxserverApp.transaction.feeCurrency">Fee Currency</Translate>
                  </Label>
                  <AvInput
                    id="transaction-feeCurrency"
                    type="select"
                    className="form-control"
                    name="feeCurrency.symbol"
                    onChange={this.feeCurrencyUpdate}
                  >
                    <option value="" key="0" />
                    {currencies
                      ? currencies.map(otherEntity => (
                          <option value={otherEntity.symbol} key={otherEntity.id}>
                            {otherEntity.symbol}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="createdBy.login">
                    <Translate contentKey="fxserverApp.transaction.createdBy">Created By</Translate>
                  </Label>
                  <AvInput
                    id="transaction-createdBy"
                    type="select"
                    className="form-control"
                    name="createdBy.id"
                    onChange={this.createdByUpdate}
                    value={isNew && users ? users[0] && users[0].id : ''}
                  >
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="updatedBy.login">
                    <Translate contentKey="fxserverApp.transaction.updatedBy">Updated By</Translate>
                  </Label>
                  <AvInput
                    id="transaction-updatedBy"
                    type="select"
                    className="form-control"
                    name="updatedBy.login"
                    onChange={this.updatedByUpdate}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/transaction" replace color="info">
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
  bankAccounts: storeState.bankAccount.entities,
  currencies: storeState.currency.entities,
  users: storeState.userManagement.users,
  transactionEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating
});

const mapDispatchToProps = {
  getBankAccounts,
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
)(TransactionUpdate);
