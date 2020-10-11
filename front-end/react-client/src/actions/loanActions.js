import axios from "axios";
import { ADD_LOAN, GET_LOAN, GET_ERRORS, GET_MEMBERSHIP } from "./types";

export const addLoan = (isbn, username) => async dispatch => {
  try {
    const response = await axios.post(`/api/loan/${isbn}/${username}`, {});

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
      `/api/users/grantMembership/${username}`,
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

export const returnLoan = (isbn, username) => async dispatch => {
  try {
    const response = await axios.delete(`/api/loan/${isbn}/${username}`, {});

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
