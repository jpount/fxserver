import axios from 'axios';
import {
  ICrudDeleteAction,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  loadMoreDataWhenScrolled,
  parseHeaderForLinks
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IBankAccount } from 'app/shared/model/bank-account.model';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export const ACTION_TYPES = {
  FETCH_BANKACCOUNT_LIST: 'bankAccount/FETCH_BANKACCOUNT_LIST',
  FETCH_BANKACCOUNT: 'bankAccount/FETCH_BANKACCOUNT',
  CREATE_BANKACCOUNT: 'bankAccount/CREATE_BANKACCOUNT',
  UPDATE_BANKACCOUNT: 'bankAccount/UPDATE_BANKACCOUNT',
  DELETE_BANKACCOUNT: 'bankAccount/DELETE_BANKACCOUNT',
  RESET: 'bankAccount/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBankAccount>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BankAccountState = Readonly<typeof initialState>;

// Reducer

export default (state: BankAccountState = initialState, action): BankAccountState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BANKACCOUNT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANKACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BANKACCOUNT):
    case REQUEST(ACTION_TYPES.UPDATE_BANKACCOUNT):
    case REQUEST(ACTION_TYPES.DELETE_BANKACCOUNT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BANKACCOUNT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANKACCOUNT):
    case FAILURE(ACTION_TYPES.CREATE_BANKACCOUNT):
    case FAILURE(ACTION_TYPES.UPDATE_BANKACCOUNT):
    case FAILURE(ACTION_TYPES.DELETE_BANKACCOUNT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKACCOUNT_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links, ITEMS_PER_PAGE)
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANKACCOUNT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANKACCOUNT):
    case SUCCESS(ACTION_TYPES.UPDATE_BANKACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANKACCOUNT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/bank-accounts';

// Actions

export const getEntities: ICrudGetAllAction<IBankAccount> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BANKACCOUNT_LIST,
    payload: axios.get<IBankAccount>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBankAccount> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANKACCOUNT,
    payload: axios.get<IBankAccount>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBankAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANKACCOUNT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBankAccount> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANKACCOUNT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBankAccount> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANKACCOUNT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
