import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { addLoan, addMembership, returnLoan } from "../../actions/loanActions";
import LoanDetails from "./LoanDetails";

class LoanBook extends Component {
  constructor() {
    super();

    this.state = {
      book_isbn: "",
      username: "",
      usernameMem: "",
      usernameRet: "",
      book_isbn_ret: "",
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onSubmitMembership = this.onSubmitMembership.bind(this);
    this.onSubmitReturnLoan = this.onSubmitReturnLoan.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    if (this.props.location.state) {
      const { book_isbn, username } = this.props.location.state;

      this.setState({
        ...this.state,
        book_isbn: "" || book_isbn,
        username: "" || username,
        usernameMem: "" || username
      });
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const createdLoan = {
      book_isbn: this.state.book_isbn,
      username: this.state.username
    };

    this.props.addLoan(this.state.book_isbn, this.state.username);
  }

  onSubmitMembership(e) {
    e.preventDefault();
    console.log("222222222222222");
    this.props.addMembership(this.state.usernameMem);
  }

  onSubmitReturnLoan(e) {
    e.preventDefault();

    console.log("SDADASD");

    this.props.returnLoan(this.state.book_isbn_ret, this.state.usernameRet);
  }

  render() {
    const { errors } = this.state;
    const { loan, membership } = this.props;

    return (
      <div className="container my-2">
        <div className="row">
          <div className="col-md-12">
            {Object.values(errors)[0] && (
              <div className="alert alert-danger mt-2" role="alert">
                {Object.values(errors)[0]}
              </div>
            )}
            <div className="card mt-2">
              <h5 className="card-header">Loan Application</h5>
              <div className="card-body">
                <form onSubmit={this.onSubmit}>
                  <div className="form-row">
                    <div className="col-md-5">
                      <label htmlFor="isbn">Book ISBN</label>
                      <input
                        type="text"
                        className="form-control"
                        placeholder="10 or 13 Digit ISBN"
                        name="book_isbn"
                        value={this.state.book_isbn}
                        onChange={this.onChange}
                      />
                    </div>
                    <div className="col-md-5">
                      <label htmlFor="username">Username</label>
                      <input
                        type="text"
                        className="form-control"
                        placeholder="User's E-Mail"
                        name="username"
                        value={this.state.username}
                        onChange={this.onChange}
                      />
                    </div>
                    <div className="col-md-2">
                      <label htmlFor="m">&nbsp;</label>
                      <button
                        type="submit"
                        className="btn btn-success form-control"
                        disabled={
                          this.state.book_isbn === "" ||
                          this.state.username === ""
                        }
                      >
                        Submit
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        {loan.bookId && (
          <LoanDetails loan={loan} username={this.state.username} />
        )}

        <div className="row mt-2">
          <div className="col-md-12">
            <div className="card">
              <h5 className="card-header">Membership Application</h5>
              <div className="card-body">
                <form onSubmit={this.onSubmitMembership}>
                  <div className="form-row">
                    <div className="col-md-5">
                      <label htmlFor="username">Username</label>
                      <input
                        type="text"
                        className="form-control"
                        placeholder="User's E-Mail"
                        name="usernameMem"
                        value={this.state.usernameMem}
                        onChange={this.onChange}
                      />
                    </div>
                    <div className="col-md-2">
                      <label htmlFor="m">&nbsp;</label>
                      <button
                        type="submit"
                        className="btn btn-success form-control"
                        disabled={this.state.usernameMem === ""}
                      >
                        Add Membership
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>

            <div className="row">
              <div className="col-md-12">
                <div className="card mt-2">
                  <h5 className="card-header">Return Loan Application</h5>
                  <div className="card-body">
                    <form onSubmit={this.onSubmitReturnLoan}>
                      <div className="form-row">
                        <div className="col-md-5">
                          <label htmlFor="isbn">Book ISBN</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="10 or 13 Digit ISBN"
                            name="book_isbn_ret"
                            value={this.state.book_isbn_ret}
                            onChange={this.onChange}
                          />
                        </div>
                        <div className="col-md-5">
                          <label htmlFor="username">Username</label>
                          <input
                            type="text"
                            className="form-control"
                            placeholder="User's E-Mail"
                            name="usernameRet"
                            value={this.state.usernameRet}
                            onChange={this.onChange}
                          />
                        </div>
                        <div className="col-md-2">
                          <label htmlFor="m">&nbsp;</label>
                          <button
                            type="submit"
                            className="btn btn-success form-control"
                            disabled={
                              this.state.book_isbn_ret === "" ||
                              this.state.usernameRet === ""
                            }
                          >
                            Submit
                          </button>
                        </div>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            {membership && membership !== "" && (
              <div className="alert alert-success mt-2" role="alert">
                {membership}
              </div>
            )}
          </div>
        </div>
      </div>
    );
  }
}

LoanBook.propTypes = {
  errors: PropTypes.object.isRequired,
  addLoan: PropTypes.func.isRequired,
  addMembership: PropTypes.func.isRequired,
  returnLoan: PropTypes.func.isRequired,
  loan: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors,
  loan: state.status.loan,
  membership: state.status.membership
});

export default connect(
  mapStateToProps,
  { addLoan, addMembership, returnLoan }
)(LoanBook);
