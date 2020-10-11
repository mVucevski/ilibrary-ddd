import React, { Component } from "react";
import BookItem from "../Book/BookItem";
import { connect } from "react-redux";
import { getBooksByGenre } from "../../actions/bookActions";

class AllByGenre extends Component {
  constructor() {
    super();

    this.state = {
      genre: ""
    };
  }

  componentDidMount() {
    this.setState({
      genre: this.props.match.params.id
    });
    this.props.getBooksByGenre(this.props.match.params.id);
  }

  componentWillReceiveProps(newProps) {
    if (this.state.genre !== newProps.match.params.id) {
      this.setState({
        genre: newProps.match.params.id
      });
      this.props.getBooksByGenre(newProps.match.params.id);
    }
  }

  render() {
    const { books } = this.props.book;

    return (
      <div className="container my-2">
        <h3>Genre: '{this.state.genre && this.state.genre.toUpperCase()}'</h3>
        <hr />
        <div className="row my-3">
          {books.map(book => (
            <BookItem key={book.id} book={book} />
          ))}
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  book: state.book
});

export default connect(
  mapStateToProps,
  { getBooksByGenre }
)(AllByGenre);
