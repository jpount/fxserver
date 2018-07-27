import { Moment } from 'moment';
import { IBankAccount } from 'app/shared/model//bank-account.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { IUser } from './user.model';

export const enum TransactionState {
  CREATED = 'CREATED',
  PENDING = 'PENDING',
  PROCESSED = 'PROCESSED',
  FAILED = 'FAILED'
}

export interface ITransaction {
  id?: number;
  fromAmount?: number;
  toAmount?: number;
  feeAmount?: number;
  state?: TransactionState;
  stateDescription?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  from?: IBankAccount;
  to?: IBankAccount;
  feeCurrency?: ICurrency;
  createdBy?: IUser;
  updatedBy?: IUser;
}

export const defaultValue: Readonly<ITransaction> = {};
