import React, { Component } from "react";
import ReviewItem from "./ReviewItem";
import AddReview from "./AddReview";
import { getReviews, addReview } from "../../../actions/bookActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class BookReviewsList extends Component {
  constructor(props) {
    super();

    this.state = {
      show: false,
      addEdit: false,
      errors: ""
    };
  }

  componentDidMount() {
    this.props.getReviews(this.props.id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onAddReview(review) {
    if (review) {
      review['bookId'] = this.props.id;
      this.props.addReview(this.props.id, review);
    }
  }

  onShow() {
    this.setState({
      show: !this.state.show
    });
  }

  onShowAddEdit() {
    this.setState({
      addEdit: !this.state.addEdit
    });
  }

  render() {
    const { reviews } = this.props.book;
    const { user } = this.props.security;

    let counter = 1;
    let reviewList = "";
    let userReview;

    if (reviews) {
      reviewList = reviews.map(r => <ReviewItem key={counter++} item={r} />);
      userReview = reviews.find(e => e.userId === user.id);
    }

    let buttons = (
      <div className="text-center">
        <button onClick={this.onShow.bind(this)} className="btn btn-warning">
          {this.state.show === false ? "Show Reviews" : "Hide Reviews"}
        </button>
        <button
          hidden={this.state.addEdit}
          onClick={this.onShowAddEdit.bind(this)}
          className="ml-2 btn btn-success"
        >
          Add/Edit Review
        </button>
      </div>
    );

    return (
      <div>
        {buttons}
        {this.state.addEdit && (
          <span>
            <h4>Your Review</h4>

            <AddReview
              oldReview={userReview}
              addReview={this.onAddReview.bind(this)}
              onSubmited={this.onShowAddEdit.bind(this)}
            />
          </span>
        )}

        <br />
        {this.state.show && reviewList}
      </div>
    );
  }
}

BookReviewsList.propTypes = {
  book: PropTypes.object.isRequired,
  getReviews: PropTypes.func.isRequired,
  addReview: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  book: state.book,
  errors: state.errors,
  security: state.security
});

export default connect(
  mapStateToProps,
  { getReviews, addReview }
)(BookReviewsList);
