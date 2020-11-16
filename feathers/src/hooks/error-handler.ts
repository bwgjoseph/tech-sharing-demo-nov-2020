import errors from '@feathersjs/errors';
import { HookContext } from '@feathersjs/feathers';

const errorHandler = (context: HookContext) => {
  if (context.error) {
    const error = context.error;
    console.log(error);
    if (!error.code) {
      const newError = new errors.GeneralError("server error");
      context.error = newError;
      return context;
    }
    if (error.code === 404 || process.env.NODE_ENV === "production") {
      error.stack = null;
    }
    return context;
  }
};

export default errorHandler;
