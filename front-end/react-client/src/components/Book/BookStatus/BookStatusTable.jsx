import React, { Component } from "react";
import BookStatusItem from "./BookStatusItem";

class BookStatusTable extends Component {

  render() {
    const { reservations, loans } = this.props;
    let counter = 1;

    const statusResTable = reservations.map(r => (
      <BookStatusItem key={counter} item={r} id={counter++} />
    ));

    const statusLoansTable = loans.map(l => (
      <BookStatusItem key={counter} item={l} id={counter++} />
    ));

    return (
      <div className="col-md-9">
        <table className="table table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>From (Date)</th>
              <th>To (Date)</th>
              <th>Type</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {statusResTable}
            {statusLoansTable}
          </tbody>
        </table>
      </div>
    );
  }
}

export default BookStatusTable;
