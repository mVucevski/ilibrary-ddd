import { SET_CURRENT_USER, GET_USER_INFO } from "../actions/types";

const initialState = {
  user: {},
  validToken: false,
  role: ""
};

const booleanActionPayload = payload => {
  if (payload) {
    return true;
  } else {
    return false;
  }
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: booleanActionPayload(action.payload.user),
        user: action.payload.user,
        role: action.payload.role
      };
    case GET_USER_INFO:
      return {
        ...state,
        userInfo: action.payload
      };
    default:
      return state;
  }
}
