/*
The store ({@link https://redux.js.org/basics/store}) represents the facts
about "what happened" and the reducers that update the state according
to actions.
 */
import {
  createStore as _createStore,
  applyMiddleware,
  combineReducers,
  compose
} from "redux";
import createSagaMiddleware from "redux-saga";

// Reducers
import { reducer as fieldReducer } from "./modules/metadata";

export default function createStore(initialSate) {
  /*
  Allows us to use Redux Devtools
  {@link https://github.com/zalmoxisus/redux-devtools-extension}
   */
  const composeEnhancers =
    typeof window === "object" && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
      ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({})
      : compose;

  /*
  Set up redux-saga's. {@link https://redux-saga.js.org/}
  This will be used for all asynchronous actions (fetching & updating the server)
 */
  const sagaMiddleware = createSagaMiddleware();
  const enhancer = composeEnhancers(applyMiddleware(sagaMiddleware));

  /*
  Add the saga runner to the createStore object and return it as a single object.
   */
  return {
    ..._createStore(combineReducers({ fieldReducer }), initialSate, enhancer),
    runSaga: sagaMiddleware.run
  };
}
