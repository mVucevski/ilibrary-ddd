import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { updateBook, getBook } from "../../actions/bookActions";
import BookDoesntExist from "./BookDoesntExist";

class EditBook extends Component {
  constructor() {
    super();

    this.state = {
      id: "",
      title: "",
      authorName: "",
      isbn: "",
      coverUrl: "",
      genre: "",
      description: "",
      language: "",
      publicationDate: "",
      availableCopies: "",
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }

    const {
      id,
      title,
      authorName,
      isbn,
      coverUrl,
      genre,
      description,
      language,
      publicationDate,
      availableCopies
    } = nextProps.book;

    this.setState({
      id,
      title,
      authorName,
      isbn,
      coverUrl,
      genre,
      description,
      language,
      publicationDate,
      availableCopies
    });
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBook(id, this.props.history);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updatedBook = {
      id: this.state.id,
      title: this.state.title,
      authorName: this.state.authorName,
      isbn: this.state.isbn,
      coverUrl: this.state.coverUrl,
      genre: this.state.genre,
      description: this.state.description,
      language: this.state.language,
      publicationDate: this.state.publicationDate,
      availableCopies: this.state.availableCopies
    };

    this.props.updateBook(this.state.isbn, updatedBook, this.props.history);
  }

  render() {
    const { errors } = this.state;
    if (errors.isbn) {
      return <BookDoesntExist error={errors.isbn} />;
    } else {
      return (
        <div>
          <div className="project">
            <div className="container">
              <div className="row">
                <div className="col-md-8 m-auto">
                  <h5 className="display-4 text-center">Edit Book</h5>
                  <hr />
                  <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.title
                        })}
                        placeholder="Book Title"
                        name="title"
                        value={this.state.title}
                        onChange={this.onChange}
                      />
                      {errors.title && (
                        <div className="invalid-feedback">{errors.title}</div>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.authorName
                        })}
                        placeholder="Author's Name"
                        name="authorName"
                        value={this.state.authorName}
                        onChange={this.onChange}
                      />
                      {errors.authorName && (
                        <div className="invalid-feedback">
                          {errors.authorName}
                        </div>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.isbn
                        })}
                        placeholder="ISBN"
                        name="isbn"
                        value={this.state.isbn}
                        disabled
                      />
                      {errors.isbn && (
                        <div className="invalid-feedback">{errors.isbn}</div>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.language
                        })}
                        placeholder="Language"
                        name="language"
                        value={this.state.language}
                        onChange={this.onChange}
                      />
                      {errors.language && (
                        <div className="invalid-feedback">
                          {errors.language}
                        </div>
                      )}
                    </div>
                    <div className="form-group">
                      <textarea
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.description
                        })}
                        placeholder="Book Description"
                        name="description"
                        value={this.state.description}
                        onChange={this.onChange}
                      />
                      {errors.descirption && (
                        <div className="invalid-feedback">
                          {errors.description}
                        </div>
                      )}
                    </div>
                    <div className="form-group">
                      <label htmlFor="genre">Book Genre</label>
                      <select
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.genre
                        })}
                        id="genre"
                        name="genre"
                        value={this.state.genre}
                        onChange={this.onChange}
                      >
                        <option>Choose...</option>
                        <option>Non-Fiction</option>
                        <option>Fiction</option>
                        <option>Cook Book</option>
                      </select>
                      {errors.genre && (
                        <div className="invalid-feedback">{errors.genre}</div>
                      )}
                    </div>

                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.coverUrl
                        })}
                        placeholder="Book Cover Image Url"
                        name="coverUrl"
                        value={this.state.coverUrl}
                        onChange={this.onChange}
                        disabled
                      />
                      {errors.coverUrl && (
                        <div className="invalid-feedback">
                          {errors.coverUrl}
                        </div>
                      )}
                    </div>

                    <div className="form-group">
                      <label htmlFor="genre">Publication Date</label>
                      <input
                        type="date"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.publicationDate
                        })}
                        name="publicationDate"
                        value={this.state.publicationDate}
                        onChange={this.onChange}
                      />
                      {errors.publicationDate && (
                        <div className="invalid-feedback">
                          {errors.publicationDate}
                        </div>
                      )}
                    </div>

                    <div className="form-group">
                      <input
                        type="text"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.availableCopies
                        })}
                        placeholder="Available Copies"
                        name="availableCopies"
                        value={this.state.availableCopies}
                        onChange={this.onChange}
                      />
                      {errors.availableCopies && (
                        <div className="invalid-feedback">
                          {errors.availableCopies}
                        </div>
                      )}
                    </div>

                    <input
                      type="submit"
                      className="btn btn-success btn-block mt-4"
                    />
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      );
    }
  }
}

EditBook.propTypes = {
  errors: PropTypes.object.isRequired,
  updateBook: PropTypes.func.isRequired,
  getBook: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  book: state.book.book
});

export default connect(
  mapStateToProps,
  { updateBook, getBook }
)(EditBook);
