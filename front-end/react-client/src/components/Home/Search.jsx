import React, { Component } from "react";
import BookItem from "../Book/BookItem";
import { connect } from "react-redux";
import { searchBooks } from "../../actions/bookActions";

class Search extends Component {
  constructor() {
    super();

    this.state = {
      search: ""
    };
  }

  componentDidMount() {
    this.setState({
      search: this.props.match.params.id
    });
    this.props.searchBooks(this.props.match.params.id);
  }

  componentWillReceiveProps(newProps) {
    if (this.state.search !== newProps.match.params.id) {
      this.setState({
        search: newProps.match.params.id
      });
      this.props.searchBooks(newProps.match.params.id);
    }
  }

  render() {
    const { books } = this.props.book;

    return (
      <div className="container my-2">
        <h3>Search results for: '{this.props.match.params.id}'</h3>
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
  { searchBooks }
)(Search);
