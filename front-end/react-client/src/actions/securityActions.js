import axios from "axios";
import {
  GET_ERRORS,
  SET_CURRENT_USER,
  GET_USER_INFO,
  GET_MEMBERSHIP,
} from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

const path = "http://localhost:8085/api/users"

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post(`${path}/register`, newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const login = (LoginRequest) => async (dispatch) => {
  try {
    const res = await axios.post(
      `${path}/login`,
      LoginRequest
    );

    // extract the token
    const { token, role } = res.data;

    //store the token in local Storage
    localStorage.setItem("jwtToken", token);

    //set role
    localStorage.setItem("userRole", role);

    // set out token in header ***
    setJWTToken(token);

    //decode token on React
    const decoded = jwt_decode(token);

    //dispatch to our securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: {
        user: decoded,
        role: role,
      },
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");
  localStorage.removeItem("userRole");
  setJWTToken(false);
  window.location.href = "/";

  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};

export const getUserInfo = (username) => async (dispatch) => {
  try {
    let response;
    if (username === undefined || username === "") {
      response = await axios.get(`${path}/currentUser`);
    } else {
      response = await axios.get(`${path}/${username}`);
    }
    dispatch({
      type: GET_USER_INFO,
      payload: response.data,
    });
  } catch (error) {}
};
