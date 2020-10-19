import React, { Component } from "react";

class AddReview extends Component {
  constructor(props) {
    super(props);
    if (props.oldReview) {
      this.state = {
        rating: props.oldReview.rating + "",
        comment: props.oldReview.content
      };
    } else {
      this.state = {
        rating: "",
        comment: ""
      };
    }

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.oldReview) {
      this.setState({
        rating: nextProps.oldReview.rating + "",
        comment: nextProps.oldReview.comment
      });
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit(e) {
    e.preventDefault();
    const updatedReview = {
      rating: this.state.rating,
      content: this.state.comment
    };
    this.props.addReview(updatedReview);
    this.props.onSubmited();
  }

  render() {
    return (
      <form onSubmit={this.onSubmit}>
        <div className="form-group">
          <label htmlFor="exampleFormControlSelect1">Rating</label>
          <div className="row">
            <div className="col-md-2 star-col">
              <div className="starrating risingstar d-flex justify-content-center flex-row-reverse">
                <input
                  type="radio"
                  id="star5"
                  name="rating"
                  value="5"
                  onChange={this.onChange}
                  checked={this.state.rating === "5"}
                />
                <label htmlFor="star5" title="5 star">
                  <i className="fas fa-star" />
                </label>
                <input
                  type="radio"
                  id="star4"
                  name="rating"
                  value="4"
                  onChange={this.onChange}
                  checked={this.state.rating === "4"}
                />
                <label htmlFor="star4" title="4 star">
                  <i className="fas fa-star" />
                </label>
                <input
                  type="radio"
                  id="star3"
                  name="rating"
                  value="3"
                  onChange={this.onChange}
                  checked={this.state.rating === "3"}
                />
                <label htmlFor="star3" title="3 star">
                  <i className="fas fa-star" />
                </label>
                <input
                  type="radio"
                  id="star2"
                  name="rating"
                  value="2"
                  onChange={this.onChange}
                  checked={this.state.rating === "2"}
                />
                <label htmlFor="star2" title="2 star">
                  <i className="fas fa-star" />
                </label>
                <input
                  type="radio"
                  id="star1"
                  name="rating"
                  value="1"
                  onChange={this.onChange}
                  checked={this.state.rating === "1"}
                />
                <label htmlFor="star1" title="1 star">
                  <i className="fas fa-star" />
                </label>
              </div>
            </div>
            <div className="col-md-8" />
          </div>
        </div>
        <div className="form-group">
          <label htmlFor="exampleFormControlTextarea1">Review</label>
          <textarea
            className="form-control"
            rows="3"
            name="comment"
            value={this.state.comment}
            onChange={this.onChange}
          />
        </div>
        <div className="row">
          <div className="col-md-9" />
          <div className="col-md-3">
            <input
              type="submit"
              disabled={!this.state.rating}
              className="btn btn-success btn-block mt-4"
            />
          </div>
        </div>
      </form>
    );
  }
}

export default AddReview;
