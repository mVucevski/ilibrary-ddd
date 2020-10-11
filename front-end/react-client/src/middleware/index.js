import { GET_ERRORS } from "../actions/types";
import { logout } from "../actions/securityActions";

const invalidTokenMsg = "Expired token";

export function expiredTokenMiddleware({ dispatch }) {
  return function(next) {
    return function(action) {
      //console.log("MIDDLEWARE:", action.payload);
      if (action.type === GET_ERRORS) {
        if (action.payload.unauthorizedAccess === invalidTokenMsg) {
          return dispatch(logout());
        }
      }
      return next(action);
    };
  };
}
