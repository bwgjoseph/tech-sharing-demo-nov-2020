import { AuthenticationRequest } from '@feathersjs/authentication/lib';
import { HookContext } from '@feathersjs/feathers';
import has from 'lodash/has';

const paramsSeen = new WeakSet();

interface X {
  path: string;
  method: string;
  type: string;
}
interface ForwardedParams {
  caller?: X;
  forwarded?: boolean;
  [key: string]: string | boolean | X | undefined | AuthenticationRequest;
}

interface Y {
  only?: Array<string>;
  omit?: Array<string>;
}

const addParamsForward = () => {
  return (context: HookContext): void => {
    if (paramsSeen.has(context.params)) {
      throw new Error(
        `Params should never be shared across services, always use params.forward(). Fix calls to ${context.path}#${context.method}`
      );
    }

    paramsSeen.add(context.params);

    // query, route, provider, headers, authentication, authenticated, user
    context.params.forward = ({ only, omit = [] }: Y = {}) => {
      const { params } = context;

      // standard set of params to be forwarded
      const forwardedParams: ForwardedParams = {
        caller: {
          path: context.path,
          method: context.method,
          type: context.type,
        },
        forwarded: true,
        // need to pass the provider?
      };

      // determine what params to forward, either chosen by caller, or set to default
      // to add custom params in here too e.g skipHook, etc
      (only || ['authenticated', 'user']).forEach((param) => {
        if (has(params, param) && !omit.includes(param)) {
          forwardedParams[param] = params[param];
        }
      });

      if (forwardedParams.authenticated) {
        forwardedParams.authentication = params.authentication;
      }

      return forwardedParams;
    };
  };
};

export default addParamsForward;
