// Application hooks that run for every service
// Don't remove this comment. It's needed to format import lines nicely.

import { trace } from 'feathers-debugger-service';
import errorHandler from './hooks/error-handler';
import addParamsForward from './hooks/forward-params';

export default {
  before: {
    all: [
      addParamsForward(),
      trace({
        captureParams: true,  // captures hook.params, default is false (optional)
        captureResult: true,  // captures hook.result, default is false (optional)
        captureQuery: true // captures hook.params.query, default is false (optional)
      })
    ],
    find: [],
    get: [],
    create: [],
    update: [],
    patch: [],
    remove: []
  },

  after: {
    all: [],
    find: [],
    get: [],
    create: [],
    update: [],
    patch: [],
    remove: []
  },

  error: {
    all: [errorHandler],
    find: [],
    get: [],
    create: [],
    update: [],
    patch: [],
    remove: []
  },

  finally: {
    all: [
      trace({
        captureParams: true,  // captures hook.params, default is false (optional)
        captureResult: true,  // captures hook.result, default is false (optional)
        captureQuery: true // captures hook.params.query, default is false (optional)
      }) // < ------- in finally "all" (last item) MUST be included!
    ],
  },
};
