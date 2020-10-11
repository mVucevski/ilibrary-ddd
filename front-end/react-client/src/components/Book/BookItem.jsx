import React, { Component } from "react";
import { Link } from "react-router-dom";
import StarRating from "./StarRating";

class BookItem extends Component {
  render() {
    const { book } = this.props;
    return (
      <div className="col-lg-2 col-md-4 mb-4 d-flex align-items-stretch">
        <div className="card h-100 my-card">
          <Link
            to={`/book/${book.id}`}
            style={{ textDecoration: "none" }}
            className="cards-a"
          >
            <div className="card add-pointer">
              <img
                className="card-img-top card-img"
                src={`http://localhost:8081/api/books/image/${book.coverUrl}`}
                alt=""
              />
              <div className="card-body h-100">
                <div className="card-title card-Title">{book.title}</div>
                <div className="card-text card-Author">{book.author}</div>

                <StarRating rating={book.rating} />
                <div className="card-text">
                  Available: {book.availableCopies > 0 ? "Yes" : "No"}
                </div>
              </div>
            </div>
          </Link>
          <div className="card-footer">
            {/*
            <button className="btn btn-danger btn-block api-remove-from-cart">
            In Cart
          </button> 
            */}

            <Link
              to={`/book/${book.id}`}
              style={{ textDecoration: "none" }}
              className="btn btn-success btn-block api-add-to-cart"
            >
              See More
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

export default BookItem;
