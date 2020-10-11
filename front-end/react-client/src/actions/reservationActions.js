import axios from "axios";
import {
  GET_RESERVATIONS,
  DELETE_RESERVATION,
  ADD_RESERVATION,
  GET_RESERVATIONS_AND_LOANS,
  GET_ERRORS
} from "./types";
import { getBook } from "./bookActions";

export const getReservationsAndLoans = (bookid) => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8082/api/lending/${bookid}`);
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
    const response = await axios.get(`http://localhost:8082/api/lending/reservations/${id}/create`);
    //console.log("add Reservetion: ", response);

    dispatch(getBook(id, "null"));
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const removeReservation = (reseravtionId, bookId) => async dispatch => {
  try {
    await axios.delete(`http://localhost:8082/api/lending/reservations/${reseravtionId}/remove`);

    dispatch(getBook(bookId, "null"));
  } catch (error) {
    //console.log("Remove Reservation Error:", error.response.data);
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};
