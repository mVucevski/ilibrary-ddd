import React, { Component } from "react";
import BookItem from "../Book/BookItem";
import { connect } from "react-redux";
import { getBooks } from "../../actions/bookActions";
import PropTypes from "prop-types";
import StarRating from "../Book/StarRating";

class Home extends Component {
  componentDidMount() {
    this.props.getBooks();
  }

  render() {
    const { books } = this.props.book;

    return (
      <div className="container my-2">
        <h3>Latest Books</h3>
        <hr />
        <div className="row my-3">
          {books.map((book) => (
            <BookItem key={book.id} book={book} />
          ))}
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  book: state.book,
});

export default connect(mapStateToProps, { getBooks })(Home);
