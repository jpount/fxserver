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

import { defaultValue, ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export const ACTION_TYPES = {
  FETCH_CURRENCYRATE_LIST: 'currencyRate/FETCH_CURRENCYRATE_LIST',
  FETCH_CURRENCYRATE: 'currencyRate/FETCH_CURRENCYRATE',
  CREATE_CURRENCYRATE: 'currencyRate/CREATE_CURRENCYRATE',
  UPDATE_CURRENCYRATE: 'currencyRate/UPDATE_CURRENCYRATE',
  DELETE_CURRENCYRATE: 'currencyRate/DELETE_CURRENCYRATE',
  RESET: 'currencyRate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICurrencyRate>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CurrencyRateState = Readonly<typeof initialState>;

// Reducer

export default (state: CurrencyRateState = initialState, action): CurrencyRateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CURRENCYRATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CURRENCYRATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CURRENCYRATE):
    case REQUEST(ACTION_TYPES.UPDATE_CURRENCYRATE):
    case REQUEST(ACTION_TYPES.DELETE_CURRENCYRATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CURRENCYRATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CURRENCYRATE):
    case FAILURE(ACTION_TYPES.CREATE_CURRENCYRATE):
    case FAILURE(ACTION_TYPES.UPDATE_CURRENCYRATE):
    case FAILURE(ACTION_TYPES.DELETE_CURRENCYRATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCYRATE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links, ITEMS_PER_PAGE)
      };
    case SUCCESS(ACTION_TYPES.FETCH_CURRENCYRATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CURRENCYRATE):
    case SUCCESS(ACTION_TYPES.UPDATE_CURRENCYRATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CURRENCYRATE):
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

const apiUrl = 'api/currency-rates';

// Actions

export const getEntities: ICrudGetAllAction<ICurrencyRate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENCYRATE_LIST,
    payload: axios.get<ICurrencyRate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICurrencyRate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CURRENCYRATE,
    payload: axios.get<ICurrencyRate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICurrencyRate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CURRENCYRATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICurrencyRate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CURRENCYRATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICurrencyRate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CURRENCYRATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
