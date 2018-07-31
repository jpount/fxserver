import { ICurrency } from 'app/shared/model//currency.model';
import { IUser } from './user.model';

export const enum BackAccountState {
  CREATED = 'CREATED',
  ACTIVE = 'ACTIVE',
  BLOCKED = 'BLOCKED',
  CLOSED = 'CLOSED'
}

export interface IBankAccount {
  id?: number;
  bsb?: string;
  bic?: string;
  name?: string;
  amount?: number;
  state?: BackAccountState;
  stateDescription?: string;
  currency?: ICurrency;
  user?: IUser;
}

export const defaultValue: Readonly<IBankAccount> = {};
