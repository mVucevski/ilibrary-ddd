import React, { Component } from "react";
import classnames from "classnames";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { addBook } from "../../actions/bookActions";
import axios from "axios";
import FormData from "form-data";

class AddBook extends Component {
  constructor() {
    super();

    this.state = {
      title: "",
      author: "",
      isbn: "",
      coverUrl: "",
      genre: "",
      description: "",
      language: "",
      publicationDate: new Date().toJSON().slice(0, 10),
      availableCopies: "",
      imageFile: "",
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChangeImg = this.onChangeImg.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange(e) {
    if (e.target.name === "coverUrl") {
      this.setState({
        imageFile: e.target.files[0]
      });

      console.log(e.target.files[0]);
    }
    this.setState({ [e.target.name]: e.target.value });
  }

  onChangeImg(e) {}

  onSubmit(e) {
    e.preventDefault();

    this.setState({ errors: {} });

    const newBook = {
      title: this.state.title,
      author: this.state.author,
      isbn: this.state.isbn,
      coverUrl: this.state.coverUrl.split("h\\")[1],
      genre: this.state.genre,
      description: this.state.description,
      publicationDate: this.state.publicationDate,
      language: this.state.language,
      availableCopies: this.state.availableCopies
    };

    const formData = new FormData();
    formData.append("file", this.state.imageFile);

    const res = axios
      .post("http://localhost:8081/api/books/uploadImg", formData, {
        headers: { "content-type": "multipart/form-data" }
      })
      .then(response => {
        console.log("UIMG REPOSNE:", response);
        console.log("NEW BOOK:", newBook);

        this.props.addBook(newBook, this.props.history);
      })
      .catch(error => {
        if (error.response) {
          this.setState({
            errors: {
              ...this.state.errors,
              coverUrl: error.response.data.imageFile
            }
          });
        }
      });

    // this.props.addBook(newBook, this.props.history);
  }
  render() {
    const { errors } = this.state;

    return (
      <div>
        <div className="project">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h5 className="display-4 text-center">Add Book</h5>
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
                        "is-invalid": errors.author
                      })}
                      placeholder="Author's Name"
                      name="author"
                      value={this.state.author}
                      onChange={this.onChange}
                    />
                    {errors.author && (
                      <div className="invalid-feedback">
                        {errors.author}
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
                      onChange={this.onChange}
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
                      <div className="invalid-feedback">{errors.language}</div>
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
                      <option>Fantasy</option>
                      <option>History</option>
                      <option>Thriller</option>
                    </select>
                    {errors.genre && (
                      <div className="invalid-feedback">{errors.genre}</div>
                    )}
                  </div>

                  <div className="form-group">
                    <input
                      type="file"
                      className={classnames("form-control form-control-lg", {
                        "is-invalid": errors.coverUrl
                      })}
                      placeholder="Book Cover Image Url"
                      name="coverUrl"
                      value={this.state.coverUrl}
                      onChange={this.onChange}
                    />
                    {errors.coverUrl && (
                      <div className="invalid-feedback">{errors.coverUrl}</div>
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

AddBook.propTypes = {
  addBook: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { addBook }
)(AddBook);
