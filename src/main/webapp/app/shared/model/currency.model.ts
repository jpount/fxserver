export interface ICurrency {
  id?: number;
  symbol?: string;
  name?: string;
  rate?: number;
}

export const defaultValue: Readonly<ICurrency> = {};
