import { IBankAccount } from 'app/shared/model//bank-account.model';
import { ICurrency } from 'app/shared/model//currency.model';

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
  uuid?: string;
  from?: IBankAccount;
  to?: IBankAccount;
  feeCurrency?: ICurrency;
}

export const defaultValue: Readonly<ITransaction> = {};
