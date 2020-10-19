import React, { Component } from "react";
import { getBook, deleteBook } from "../../actions/bookActions";
import {
  addReservation,
  removeReservation
} from "../../actions/reservationActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import BookDoesntExist from "./BookDoesntExist";
import ReservationButton from "./ReservationButton";
import BookStatusTable from "./BookStatus/BookStatusTable";
import AddReview from "./BookReviews/AddReview";
import StarRating from "./StarRating";
import BookReviewsList from "./BookReviews/BookReviewsList";
import { dateConverter } from "../../dateFormatter";
import BookLendingTable from "./BookStatus/BookLendingTable"
import {getReservationsAndLoans} from "../../actions/reservationActions";

class BookDetails extends Component {
  constructor() {
    super();

    this.state = {
      errors: {},
      reserved: false,
      activeUserReservationId: undefined
    };
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getBook(id, this.props.history);
    this.props.getReservationsAndLoans(this.props.id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }

    const { user } = this.props.security;
    let reservations;

    if(nextProps.status){
      reservations = nextProps.status.reservations;
    }else{
      reservations = this.props.status.reservations;
    }

    

    if (reservations) {

      let reservation = reservations.find(
        item => item.userId === user.id
      )

      if(reservation){
        this.setState({
          reserved: true,
          activeUserReservationId: reservation.reservationId
        });
      }else{
        this.setState({
          reserved: false
        });
      }

     
    }
  }

  onDelete(isbn) {
    this.props.deleteBook(isbn, this.props.history);
  }

  onReservIt() {
    if (this.state.reserved) {
      this.props.removeReservation(this.state.activeUserReservationId, this.props.book.book.id);
    } else {
      this.props.addReservation(this.props.book.book.id);
    }
  }

  isReserved(){
    this.setState({reserved: true})
  }

  render() {
    const { id } = this.props.match.params;
    const { book } = this.props.book;
    const { errors } = this.state;
    const { status } = this.props;

    let pageContent;
    let checkRes = false;
    let pageError;

    if (errors.isbn) {
      pageContent = <BookDoesntExist error={errors.isbn} />;
    } //else if (errors.availableCopies) {
    // Tmp, I will fix it
    // pageContent = <BookDoesntExist error={errors.availableCopies} />;
    //pageError = <BookDoesntExist error={errors.availableCopies} />;
    //}
    else {
      let employeeButtons = (
        <div className="pt-2">
          <Link
            to={{
              pathname: `/statusManager`,
              state: {
                book_isbn: book.id,
                username: ""
              }
            }}
            className="btn btn-block btn-info"
          >
            Create Loan
          </Link>
          <Link
            to={`/book/${book.id}/edit`}
            className="btn btn-block btn-secondary"
          >
            Edit
          </Link>
          <button
            className="btn btn-block btn-danger"
            onClick={this.onDelete.bind(this, id)}
          >
            Delete
          </button>
        </div>
      );

      pageContent = (
        <div>
          {errors.availableCopies && (
            <BookDoesntExist error={errors.availableCopies} />
          )}
          {Object.values(errors)[0] &&  <BookDoesntExist error={Object.values(errors)[0]} />}
          <h4 style={{ fontWeight: "bold" }}>{book.title}</h4>
          <hr />
          <dt />

          <div className="row py-4">
            <div className="col-md-3">
            {book.coverUrl && <img
              src={`http://localhost:8081/api/books/image/${book.coverUrl}`}
              className="img-fluid"
              alt=""
            />}  
            
            </div>
            <div className="col-md-6">
              <p className="lh">
                <strong>By (author): </strong>
                {book.author}
              </p>
              <p className="lh">
                <strong>Language: </strong> {book.language}
              </p>
              <p className="lh">
                <strong>ISBN: </strong>
                {book.isbn}
              </p>
              <p className="lh">
                <strong>Category: </strong> {book.genre}
              </p>
              <p className="lh">
                <strong>Available as e-Book: </strong> No
              </p>
              <div className="lh">
                <strong>Rating: </strong>
                <StarRating rating={book.rating} />
                {book.rating === 0 ? (
                  " No ratings yet!"
                ) : (
                  <span className="ml-1">{book.rating}/5</span>
                )}
              </div>
              <br />
              <p className="lh">
                <strong>Publication date:</strong>{" "}
                {dateConverter(book.publicationDate)}
              </p>
              <strong>Description:</strong>
              <p>{book.description}</p>
            </div>

            <div className="col-md-3 bookDetailsBorder">
              <div className="pt-2">
                <p
                  style={{ color: book.availableCopies > 0 ? "green" : "red" }}
                  className="bookDetailsAvailable"
                >
                  <i
                    className={
                      "fa " +
                      (book.availableCopies > 0 ? "fa-check-circle" : "fa-times")
                    }
                  />
                  {book.availableCopies > 0 ? " Available" : " Not Available"}
                </p>

                <ReservationButton
                  reserved={this.state.reserved}
                  onReservIt={this.onReservIt.bind(this)}
                />
              </div>

              {this.props.security.role === "ROLE_EMPLOYEE" && employeeButtons}
            </div>
          </div>

          <div>
            <h4>Status</h4>
            <hr />
            {book.reservations && book.loans && (
              <BookStatusTable
                reservations={book.reservations}
                loans={book.loans}
              />
            )}
            { book.id &&<BookLendingTable id={book.id}  activeUser={this.props.security.user.id} status={status}></BookLendingTable>}
          </div>
          <hr />
          {/* {book.reservations && <BookReviewsList id={book.id} />} */}
          { book.id && <BookReviewsList id={book.id} />}
        </div>
      );
    }

    return <div className="container">{pageContent}</div>;
  }
}

BookDetails.propTypes = {
  book: PropTypes.object.isRequired,
  getBook: PropTypes.func.isRequired,
  deleteBook: PropTypes.func.isRequired,
  addReservation: PropTypes.func.isRequired,
  getReservationsAndLoans: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  book: state.book,
  errors: state.errors,
  security: state.security,
  status: state.status
});

export default connect(
  mapStateToProps,
  { getBook, deleteBook, addReservation, removeReservation, getReservationsAndLoans }
)(BookDetails);
