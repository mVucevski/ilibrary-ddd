import { GET_LOAN, GET_MEMBERSHIP, GET_RESERVATIONS_AND_LOANS } from "../actions/types";

const initialState = {
  loan: {},
  membership: ""
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_LOAN:
      return {
        ...state,
        loan: action.payload
      };
    case GET_MEMBERSHIP:
      return {
        ...state,
        membership: action.payload
      };
      case GET_RESERVATIONS_AND_LOANS:
        return {
          ...state,
          loans: action.payload.loans,
          reservations: action.payload.reservations
        };
    default:
      return state;
  }
}
