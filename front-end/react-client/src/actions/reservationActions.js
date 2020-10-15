import axios from "axios";
import {
  GET_RESERVATIONS,
  DELETE_RESERVATION,
  ADD_RESERVATION,
  GET_RESERVATIONS_AND_LOANS,
  GET_ERRORS
} from "./types";

const path = "http://localhost:8082/api/lending";

export const getReservationsAndLoans = (bookid) => async (dispatch) => {
  try {
    const response = await axios.get(`${path}/${bookid}`);

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

export const addReservation = id => async dispatch => {
  try {
    const response = await axios.get(`${path}/reservations/${id}/create`);

    dispatch(getReservationsAndLoans(id));
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const removeReservation = (reseravtionId, bookId) => async dispatch => {
  try {
    await axios.delete(`${path}/reservations/${reseravtionId}/remove`);

    dispatch(getReservationsAndLoans(bookId));
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
