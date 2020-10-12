import axios from "axios";
import { ADD_LOAN, GET_LOAN, GET_ERRORS, GET_MEMBERSHIP, GET_RESERVATIONS_AND_LOANS } from "./types";

export const addLoan = (bookId, username) => async dispatch => {
  try {
    const response = await axios.post(`http://localhost:8082/api/lending/loans/${bookId}`, {bookId: bookId, username: username});

    dispatch({
      type: GET_LOAN,
      payload: response.data
    });

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const addMembership = username => async dispatch => {
  try {
    const response = await axios.post(
      `http://localhost:8085/api/users/grantMembership/${username}`,
      {}
    );

    dispatch({
      type: GET_MEMBERSHIP,
      payload: response.data
    });

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const returnLoan = (bookId, username) => async dispatch => {
  try {
    const response = await axios.delete(`http://localhost:8082/api/lending/loans/${bookId}/${username}`, {});

    dispatch({
      type: GET_MEMBERSHIP,
      payload: response.data
    });

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getReservationsAndLoansForUser = (userId) => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8082/api/lending/user/${userId}`);

    dispatch({
      type: GET_RESERVATIONS_AND_LOANS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
