import axios from "axios";
import {
  GET_ERRORS,
  GET_BOOKS,
  GET_BOOK,
  DELETE_BOOK,
  GET_REVIEWS,
} from "./types";

export const addBook = (book, history) => async (dispatch) => {
  try {
    const response = await axios.post("http://localhost:8081/api/books", book);
    history.push("/");
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

export const getBooks = () => async (dispatch) => {
  try {
    const response = await axios.get("http://localhost:8081/api/books");

    console.log("Get Books Response: ", response);

    dispatch({
      type: GET_BOOKS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getBook = (id, history) => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8081/api/books/${id}`);
    dispatch({
      type: GET_BOOK,
      payload: response.data,
    });
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    //console.log("ERROR:", error);

    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const deleteBook = (isbn, history) => async (dispatch) => {
  if (window.confirm("Are you sure? This book will be deleted permanently!")) {
    await axios.delete(`http://localhost:8081/api/books/${isbn}`);
    dispatch({
      type: DELETE_BOOK,
      payload: isbn,
    });
    history.push("/");
  }
};

export const updateBook = (id, updatedBook, history) => async (dispatch) => {
  try {
    await axios.post(`"http://localhost:8081/api/books"`, updatedBook);
    history.push(`/book/${id}`);
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

export const addReview = (id, review) => async (dispatch) => {
  try {
    const response = await axios.post(`http://localhost:8083/api/reviews`, review);

    dispatch(getBook(id, "null"));
    dispatch(getReviews(id));
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getReviews = (id) => async (dispatch) => {
  try {
    const response = await axios.get(`http://localhost:8083/api/reviews/${id}`);
    dispatch({
      type: GET_REVIEWS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const searchBooks = (keyword) => async (dispatch) => {
  try {
    if (keyword === undefined || keyword === "") {
      keyword = " ";
    }
    console.log("SEARC:", keyword);

    const response = await axios.get(`/api/book/search/${keyword}`);

    dispatch({
      type: GET_BOOKS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getBooksByGenre = (keyword) => async (dispatch) => {
  try {
    const response = await axios.get(`/api/book/category/${keyword}`);
    dispatch({
      type: GET_BOOKS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
