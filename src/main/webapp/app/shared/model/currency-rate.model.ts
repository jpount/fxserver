import { ICurrency } from 'app/shared/model//currency.model';

export interface ICurrencyRate {
  id?: number;
  rate?: number;
  currency?: ICurrency;
}

export const defaultValue: Readonly<ICurrencyRate> = {};
